<template>
  <div class="gallery">
    <div v-for="concept in concepts" :key="concept.title" class="card">
      <img :src="concept.imageUrl" :alt="concept.title" class="image" />
      <div class="content">
        <h2>{{ concept.title }}</h2>
        <p><strong>Prompt Artist:</strong> {{ concept.promptArtist }}</p>
        <p><strong>AI Tool:</strong> {{ concept.aiTool }}</p>
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

onMounted(async () => {
  try {
    const res = await fetch('http://localhost:8080/concepts')
    concepts.value = await res.json()
  } catch (err) {
    console.error('Failed to fetch concepts:', err)
  }
})
</script>

<style scoped>
.gallery {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 2rem;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

/* Card layout */
.card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  overflow: hidden;
  width: 340px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
  transform: translateY(-6px);
  box-shadow: 0 6px 16px rgba(0,0,0,0.15);
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
