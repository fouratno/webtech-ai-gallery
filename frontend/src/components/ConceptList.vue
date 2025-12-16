<template>
  <div class="page">
    <section class="form-card">
      <h2>Create a new concept</h2>
      <p class="helper">All fields are required. A valid image URL keeps the gallery lively.</p>

      <form class="concept-form" @submit.prevent="submit">
        <input v-model.trim="form.title" placeholder="Concept title" required />
        <input v-model.trim="form.promptArtist" placeholder="Prompt artist" required />
        <input v-model.trim="form.aiTool" placeholder="AI tool" required />
        <input v-model.trim="form.imageUrl" placeholder="Image URL" maxlength="500" required />

        <p v-if="error" class="status error" role="alert">{{ error }}</p>
        <p v-if="success" class="status success" role="status">Concept created successfully</p>

        <button :disabled="loading" type="submit">
          {{ loading ? 'Saving...' : 'Create Concept' }}
        </button>
      </form>
    </section>

    <section class="cards-section">
      <p v-if="loadingConcepts" class="status">Loading concepts...</p>
      <p v-else-if="loadError" class="status error">Failed to load concepts: {{ loadError }}</p>
      <p v-else-if="concepts.length === 0" class="status">No concepts available yet.</p>
      <div v-else class="card-list">
        <div v-for="concept in concepts" :key="concept.id ?? concept.title" class="card">
          <img :src="concept.imageUrl" :alt="concept.title" class="image" />
          <div class="content">
            <h3>{{ concept.title }}</h3>
            <p><strong>Prompt Artist:</strong> {{ concept.promptArtist }}</p>
            <p><strong>AI Tool:</strong> {{ concept.aiTool }}</p>
          </div>
        </div>
      </div>
    </section>
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
const loading = ref(false)
const loadingConcepts = ref(true)
const loadError = ref<string | null>(null)
const error = ref<string>('')
const success = ref(false)

const form = reactive<Concept>({
  title: '',
  promptArtist: '',
  aiTool: '',
  imageUrl: ''
})

const rawApiBase = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'
const API_BASE = rawApiBase.replace(/\/+$/, '')

onMounted(async () => {
  await fetchConcepts()
})

const fetchConcepts = async () => {
  try {
    const res = await fetch(`${API_BASE}/concepts`)
    if (!res.ok) {
      throw new Error('Failed to fetch data')
    }
    concepts.value = await res.json()
  } catch (err) {
    loadError.value = err instanceof Error ? err.message : 'Failed to fetch data'
  } finally {
    loadingConcepts.value = false
  }
}

const resetForm = () => {
  form.title = ''
  form.promptArtist = ''
  form.aiTool = ''
  form.imageUrl = ''
}

const submit = async () => {
  error.value = ''
  success.value = false
  loading.value = true

  try {
    const res = await fetch(`${API_BASE}/concepts`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form)
    })

    if (!res.ok) {
      error.value = await res.text()
      return
    }

    const created = (await res.json()) as Concept
    concepts.value.unshift(created)
    success.value = true
    resetForm()
  } catch (e) {
    error.value = 'Network error'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page {
  padding: 1rem 1.5rem 3rem;
  max-width: 900px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.form-card {
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.07);
  padding: 1.5rem;
  text-align: left;
}

.helper {
  margin: 0.25rem 0 0.5rem;
  color: #4f5660;
  font-size: 0.95rem;
}

.concept-form {
  display: grid;
  gap: 0.8rem;
  margin-top: 1rem;
}

input {
  padding: 0.7rem 0.85rem;
  border: 1px solid #d0d7e2;
  border-radius: 10px;
  font-size: 1rem;
  transition: border 0.2s ease, box-shadow 0.2s ease;
}

input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

button[type='submit'] {
  padding: 0.85rem 1.2rem;
  border: none;
  border-radius: 12px;
  background: linear-gradient(120deg, #2563eb, #7c3aed);
  color: #fff;
  font-weight: 700;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease, opacity 0.2s ease;
}

button[disabled] {
  opacity: 0.7;
  cursor: not-allowed;
}

button[type='submit']:hover:not([disabled]) {
  transform: translateY(-1px);
  box-shadow: 0 10px 20px rgba(37, 99, 235, 0.2);
}

.status {
  font-size: 0.95rem;
  color: #333;
  margin: 0.25rem 0 0;
}

.status.error {
  color: #b91c1c;
}

.status.success {
  color: #166534;
}

.cards-section {
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.07);
  padding: 1.25rem;
  text-align: left;
}

.card-list {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  margin-top: 1rem;
}

.card {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 18px rgba(0, 0, 0, 0.12);
}

.image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.content {
  padding: 0.9rem 1rem 1.1rem;
}

h3 {
  margin: 0 0 0.4rem;
  font-size: 1.1rem;
  color: #111;
}

p {
  margin: 0.25rem 0;
  color: #333;
}

strong {
  color: #000;
}
</style>
