<template>
  <div class="gallery">
    <form class="concept-form" @submit.prevent="submitConcept">
      <h2>Add a new concept</h2>
      <div class="field">
        <label for="title">Title</label>
        <input id="title" v-model="form.title" type="text" required placeholder="Cozy reading nook" />
      </div>
      <div class="field">
        <label for="promptArtist">Prompt Artist</label>
        <input
          id="promptArtist"
          v-model="form.promptArtist"
          type="text"
          required
          placeholder="Studio Ghibli"
        />
      </div>
      <div class="field">
        <label for="aiTool">AI Tool</label>
        <input id="aiTool" v-model="form.aiTool" type="text" required placeholder="Midjourney" />
      </div>
      <div class="field">
        <label for="imageUrl">Image URL</label>
        <input id="imageUrl" v-model="form.imageUrl" type="url" required placeholder="https://..." />
      </div>
      <p v-if="submitError" class="status error">{{ submitError }}</p>
      <button type="submit" :disabled="submitting">
        {{ submitting ? 'Submitting...' : 'Add Concept' }}
      </button>
    </form>

    <p v-if="loading" class="status">Loading concepts...</p>
    <p v-else-if="error" class="status error">Failed to load concepts: {{ error }}</p>
    <div v-else class="card-list">
      <div v-for="concept in concepts" :key="concept.id ?? concept.title" class="card">
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
import { onMounted, reactive, ref } from 'vue'

interface Concept {
  id?: number
  title: string
  promptArtist: string
  aiTool: string
  imageUrl: string
}

const concepts = ref<Concept[]>([])
const loading = ref(true)
const error = ref<string | null>(null)
const submitting = ref(false)
const submitError = ref<string | null>(null)

const form = reactive<Concept>({
  title: '',
  promptArtist: '',
  aiTool: '',
  imageUrl: '',
})

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const loadConcepts = async () => {
  try {
    const res = await fetch(`${API_BASE_URL}/concepts`)
    if (!res.ok) {
      throw new Error('Failed to fetch data')
    }
    concepts.value = await res.json()
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Failed to fetch data'
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  form.title = ''
  form.promptArtist = ''
  form.aiTool = ''
  form.imageUrl = ''
}

const submitConcept = async () => {
  submitError.value = null
  submitting.value = true
  try {
    const res = await fetch(`${API_BASE_URL}/concepts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(form),
    })

    if (!res.ok) {
      throw new Error('Failed to create concept')
    }

    const savedConcept: Concept = await res.json()
    concepts.value.push(savedConcept)
    resetForm()
  } catch (err) {
    submitError.value = err instanceof Error ? err.message : 'Failed to create concept'
  } finally {
    submitting.value = false
  }
}

onMounted(loadConcepts)
</script>

<style scoped>
.gallery {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.concept-form {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 2rem;
  display: grid;
  gap: 1rem;
}

.concept-form h2 {
  margin: 0;
  font-size: 1.4rem;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

label {
  font-weight: 600;
}

input {
  padding: 0.75rem 0.9rem;
  border: 1px solid #dcdcdc;
  border-radius: 10px;
  font-size: 1rem;
}

button {
  background: #111;
  color: #fff;
  border: none;
  border-radius: 10px;
  padding: 0.8rem 1rem;
  font-weight: 700;
  cursor: pointer;
  transition: opacity 0.2s ease;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.status {
  text-align: center;
  font-size: 1.1rem;
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
