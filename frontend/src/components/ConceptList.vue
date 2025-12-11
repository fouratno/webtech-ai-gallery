<template>
  <div class="gallery">
    <h1>AI Interior Gallery</h1>

    <!-- New Concept Form -->
    <form class="concept-form" @submit.prevent="handleSubmit">
      <h2>Add a new concept</h2>

      <label>
        Title
        <input v-model="newConcept.title" required />
      </label>

      <label>
        Prompt Artist
        <input v-model="newConcept.promptArtist" required />
      </label>

      <label>
        AI Tool
        <input v-model="newConcept.aiTool" required />
      </label>

      <label>
        Image URL
        <input v-model="newConcept.imageUrl" required />
      </label>

      <button type="submit" :disabled="isSubmitting">
        {{ isSubmitting ? 'Saving...' : 'Save Concept' }}
      </button>

      <p v-if="error" class="error">{{ error }}</p>
      <p v-if="success" class="success">{{ success }}</p>
    </form>

    <!-- Concept Cards -->
    <div class="cards">
      <div v-for="concept in concepts" :key="concept.id ?? concept.title" class="card">
        <img :src="concept.imageUrl" :alt="concept.title" class="image" />
        <h2>{{ concept.title }}</h2>
        <p><strong>Prompt Artist:</strong> {{ concept.promptArtist }}</p>
        <p><strong>AI Tool:</strong> {{ concept.aiTool }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'

interface Concept {
  id?: number
  title: string
  promptArtist: string
  aiTool: string
  imageUrl: string
}

const concepts = ref<Concept[]>([])
const isLoading = ref(false)
const isSubmitting = ref(false)
const error = ref<string | null>(null)
const success = ref<string | null>(null)

// new concept model
const newConcept = reactive<Concept>({
  title: '',
  promptArtist: '',
  aiTool: '',
  imageUrl: ''
})

// API base URL from env (set on Render + fallback for localhost)
const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// Load existing concepts
const loadConcepts = async () => {
  try {
    isLoading.value = true
    error.value = null
    const res = await fetch(`${API_BASE_URL}/concepts`)
    if (!res.ok) {
      throw new Error(`Failed to load concepts: ${res.status}`)
    }
    concepts.value = await res.json()
  } catch (e: any) {
    error.value = e.message ?? 'Error while loading concepts'
  } finally {
    isLoading.value = false
  }
}

// Submit new concept
const handleSubmit = async () => {
  try {
    isSubmitting.value = true
    error.value = null
    success.value = null

    const res = await fetch(`${API_BASE_URL}/concepts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newConcept)
    })

    if (!res.ok) {
      throw new Error(`Failed to save concept: ${res.status}`)
    }

    const saved = await res.json()
    // add to list
    concepts.value.unshift(saved)

    // clear form
    newConcept.title = ''
    newConcept.promptArtist = ''
    newConcept.aiTool = ''
    newConcept.imageUrl = ''

    success.value = 'Concept saved successfully!'
  } catch (e: any) {
    error.value = e.message ?? 'Error while saving concept'
  } finally {
    isSubmitting.value = false
  }
}

onMounted(loadConcepts)
</script>

<style scoped>
.gallery {
  max-width: 1200px;
  margin: 2rem auto;
  font-family: "Inter", sans-serif;
}

/* Form styling */
.concept-form {
  background: #ffffff;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.08);
  margin-bottom: 2rem;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 1rem 1.5rem;
}

.concept-form h2 {
  grid-column: 1 / -1;
  margin-top: 0;
  margin-bottom: 0.5rem;
}

.concept-form label {
  display: flex;
  flex-direction: column;
  font-size: 0.9rem;
  gap: 0.25rem;
}

.concept-form input {
  padding: 0.5rem 0.75rem;
  border-radius: 8px;
  border: 1px solid #ddd;
}

.concept-form button {
  grid-column: 1 / -1;
  padding: 0.6rem 1.2rem;
  border-radius: 999px;
  border: none;
  background: #111827;
  color: #fff;
  cursor: pointer;
}

.concept-form button:disabled {
  opacity: 0.6;
  cursor: default;
}

.error {
  grid-column: 1 / -1;
  color: #b91c1c;
  font-size: 0.9rem;
}

.success {
  grid-column: 1 / -1;
  color: #15803d;
  font-size: 0.9rem;
}

/* Cards grid */
.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 1.5rem;
}

.card {
  background: #fff;
  padding: 1rem;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  height: 100%;
}

.image {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 1rem;
}

h2 {
  font-size: 1.3rem;
  margin-bottom: 0.5rem;
}

p {
  margin: 0.3rem 0;
}
</style>
