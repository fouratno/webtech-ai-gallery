<template>
  <div class="gallery">
    <p v-if="loading" class="status">Loading concepts...</p>
    <p v-else-if="error" class="status error">Failed to load concepts: {{ error }}</p>
    <p v-else-if="concepts.length === 0" class="status">No concepts available yet.</p>
    <div v-else class="card-list">
      <div v-for="concept in concepts" :key="concept.title" class="card">
        <img :src="concept.imageUrl" :alt="concept.title" class="image" />
        <div class="content">
          <h2>{{ concept.title }}</h2>
          <p><strong>Prompt Artist:</strong> {{ concept.promptArtist }}</p>
          <p><strong>AI Tool:</strong> {{ concept.aiTool }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

interface Concept {
  title: string
  promptArtist: string
  aiTool: string
  imageUrl: string
}

const concepts = ref<Concept[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

const rawApiBase = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'
const API_BASE = rawApiBase.replace(/\/+$/, '')

onMounted(async () => {
  try {
    const res = await fetch(`${API_BASE}/concepts`)
    if (!res.ok) {
      throw new Error('Failed to fetch data')
    }
    concepts.value = await res.json()
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to fetch data'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.gallery {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.status {
  text-align: center;
  font-size: 1.2rem;
  color: #333;
}

.status.error {
  color: #c0392b;
}

.card-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 2rem;
}

.card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 340px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
  transform: translateY(-6px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.image {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.content {
  padding: 1rem 1.2rem;
  text-align: left;
}

h2 {
  font-size: 1.3rem;
  margin: 0.5rem 0;
  color: #111;
}

p {
  margin: 0.3rem 0;
  color: #333;
}

strong {
  color: #000;
}
</style>
