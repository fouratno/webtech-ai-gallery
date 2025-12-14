<template>
  <div class="concept-list-container">
    <!-- Header -->
    <div class="header-section">
      <h1 class="page-title">🎨 AI Interior Gallery</h1>
      <p class="page-subtitle">Discover and create amazing AI-generated interior design concepts</p>
    </div>

    <!-- Add New Concept Form -->
    <div class="form-section">
      <h2 class="form-title">Add New Concept</h2>
      <form @submit.prevent="submitConcept" class="concept-form">
        <div class="form-grid">
          <div class="form-group">
            <label for="title" class="form-label">Title *</label>
            <input
              id="title"
              v-model="formData.title"
              type="text"
              class="form-input"
              :class="{ 'error': errors.title }"
              placeholder="Enter concept title"
              required
            />
            <span v-if="errors.title" class="error-message">{{ errors.title }}</span>
          </div>

          <div class="form-group">
            <label for="promptArtist" class="form-label">Prompt Artist</label>
            <input
              id="promptArtist"
              v-model="formData.promptArtist"
              type="text"
              class="form-input"
              placeholder="Enter artist name"
            />
          </div>

          <div class="form-group">
            <label for="aiTool" class="form-label">AI Tool</label>
            <select
              id="aiTool"
              v-model="formData.aiTool"
              class="form-input"
            >
              <option value="">Select AI Tool</option>
              <option value="DALL-E">DALL-E</option>
              <option value="Midjourney">Midjourney</option>
              <option value="Stable Diffusion">Stable Diffusion</option>
              <option value="Leonardo AI">Leonardo AI</option>
              <option value="Firefly">Adobe Firefly</option>
              <option value="Other">Other</option>
            </select>
          </div>

          <div class="form-group">
            <label for="imageUrl" class="form-label">Image URL</label>
            <input
              id="imageUrl"
              v-model="formData.imageUrl"
              type="url"
              class="form-input"
              placeholder="https://example.com/image.jpg"
            />
          </div>
        </div>

        <div class="form-actions">
          <button
            type="submit"
            class="submit-btn"
            :disabled="isSubmitting"
          >
            <span v-if="isSubmitting" class="loading-spinner"></span>
            {{ isSubmitting ? 'Creating...' : 'Create Concept' }}
          </button>
          <button
            type="button"
            @click="resetForm"
            class="reset-btn"
            :disabled="isSubmitting"
          >
            Reset
          </button>
        </div>
      </form>

      <!-- Success/Error Messages -->
      <div v-if="message.text" class="message" :class="message.type">
        {{ message.text }}
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="loading-section">
      <div class="loading-spinner large"></div>
      <p>Loading concepts...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="loadError" class="error-section">
      <h3>Failed to load concepts</h3>
      <p>{{ loadError }}</p>
      <button @click="loadConcepts" class="retry-btn">Retry</button>
    </div>

    <!-- Concepts Grid -->
    <div v-else class="concepts-section">
      <div class="concepts-header">
        <h2>Gallery ({{ concepts.length }} concepts)</h2>
        <button @click="loadConcepts" class="refresh-btn" :disabled="isLoading">
          Refresh
        </button>
      </div>

      <div v-if="concepts.length === 0" class="empty-state">
        <h3>No concepts yet</h3>
        <p>Be the first to add an AI interior design concept!</p>
      </div>

      <div v-else class="concepts-grid">
        <div
          v-for="concept in concepts"
          :key="concept.id"
          class="concept-card"
        >
          <div class="card-image">
            <img
              :src="concept.imageUrl || 'https://placehold.co/400x300?text=No+Image'"
              :alt="concept.title"
              @error="handleImageError"
              loading="lazy"
            />
          </div>
          <div class="card-content">
            <h3 class="card-title">{{ concept.title }}</h3>
            <div class="card-details">
              <p v-if="concept.promptArtist" class="card-artist">
                <strong>Artist:</strong> {{ concept.promptArtist }}
              </p>
              <p v-if="concept.aiTool" class="card-tool">
                <strong>AI Tool:</strong> {{ concept.aiTool }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { Concept, ConceptFormData } from '../types/Concept'

// API Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// Reactive data
const concepts = ref<Concept[]>([])
const isLoading = ref(false)
const isSubmitting = ref(false)
const loadError = ref<string | null>(null)

// Form data
const formData = reactive<ConceptFormData>({
  title: '',
  promptArtist: '',
  aiTool: '',
  imageUrl: ''
})

// Form validation
const errors = reactive({
  title: null as string | null
})

// Messages
const message = reactive({
  text: '',
  type: ''
})

// Load concepts from API
const loadConcepts = async (): Promise<void> => {
  isLoading.value = true
  loadError.value = null
  
  try {
    const response = await fetch(`${API_BASE_URL}/concepts`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    
    const data: Concept[] = await response.json()
    concepts.value = data
    
  } catch (error) {
    console.error('Error loading concepts:', error)
    loadError.value = error instanceof Error ? error.message : 'Failed to load concepts'
  } finally {
    isLoading.value = false
  }
}

// Validate form
const validateForm = (): boolean => {
  errors.title = null
  
  if (!formData.title.trim()) {
    errors.title = 'Title is required'
    return false
  }
  
  if (formData.title.length < 3) {
    errors.title = 'Title must be at least 3 characters'
    return false
  }
  
  return true
}

// Submit new concept
const submitConcept = async (): Promise<void> => {
  if (!validateForm()) {
    return
  }
  
  isSubmitting.value = true
  message.text = ''
  
  try {
    const conceptData: Partial<Concept> = {
      title: formData.title.trim(),
      promptArtist: formData.promptArtist.trim() || undefined,
      aiTool: formData.aiTool || undefined,
      imageUrl: formData.imageUrl.trim() || undefined
    }
    
    const response = await fetch(`${API_BASE_URL}/concepts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(conceptData)
    })
    
    if (!response.ok) {
      if (response.status === 409) {
        throw new Error('A concept with this title already exists')
      }
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    
    const savedConcept: Concept = await response.json()
    
    // Add to concepts list at the beginning
    concepts.value.unshift(savedConcept)
    
    // Reset form
    resetForm()
    
    // Show success message
    message.text = 'Concept created successfully!'
    message.type = 'success'
    
    // Clear message after 3 seconds
    setTimeout(() => {
      message.text = ''
    }, 3000)
    
  } catch (error) {
    console.error('Error creating concept:', error)
    message.text = error instanceof Error ? error.message : 'Failed to create concept'
    message.type = 'error'
  } finally {
    isSubmitting.value = false
  }
}

// Reset form
const resetForm = (): void => {
  formData.title = ''
  formData.promptArtist = ''
  formData.aiTool = ''
  formData.imageUrl = ''
  errors.title = null
}

// Handle image loading errors
const handleImageError = (event: Event): void => {
  const target = event.target as HTMLImageElement
  target.src = 'https://placehold.co/400x300?text=Image+Not+Found'
}

// Load concepts on component mount
onMounted(() => {
  loadConcepts()
})
</script>

<style scoped>
.concept-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

/* Header */
.header-section {
  text-align: center;
  margin-bottom: 3rem;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1a202c;
  margin-bottom: 0.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.page-subtitle {
  font-size: 1.1rem;
  color: #718096;
  margin: 0;
}

/* Form Section */
.form-section {
  background: #f7fafc;
  border-radius: 12px;
  padding: 2rem;
  margin-bottom: 3rem;
  border: 1px solid #e2e8f0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.form-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 1.5rem;
}

.concept-form {
  max-width: 800px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  font-weight: 500;
  color: #4a5568;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
}

.form-input {
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s, box-shadow 0.2s;
  background: white;
}

.form-input:focus {
  outline: none;
  border-color: #4299e1;
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.1);
}

.form-input.error {
  border-color: #e53e3e;
}

.error-message {
  color: #e53e3e;
  font-size: 0.8rem;
  margin-top: 0.25rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.submit-btn {
  background: linear-gradient(135deg, #4299e1 0%, #3182ce 100%);
  color: white;
  border: none;
  padding: 0.75rem 2rem;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  box-shadow: 0 2px 4px rgba(66, 153, 225, 0.2);
}

.submit-btn:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(66, 153, 225, 0.3);
}

.submit-btn:disabled {
  background: #a0aec0;
  cursor: not-allowed;
  transform: none;
}

.reset-btn {
  background: #e2e8f0;
  color: #4a5568;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.reset-btn:hover:not(:disabled) {
  background: #cbd5e0;
}

.reset-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Messages */
.message {
  padding: 1rem;
  border-radius: 8px;
  margin-top: 1rem;
  font-weight: 500;
}

.message.success {
  background: #c6f6d5;
  color: #22543d;
  border: 1px solid #9ae6b4;
}

.message.error {
  background: #fed7d7;
  color: #742a2a;
  border: 1px solid #fc8181;
}

/* Loading */
.loading-section {
  text-align: center;
  padding: 3rem;
  color: #718096;
}

.loading-spinner {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 2px solid #e2e8f0;
  border-radius: 50%;
  border-top-color: #4299e1;
  animation: spin 1s ease-in-out infinite;
}

.loading-spinner.large {
  width: 40px;
  height: 40px;
  border-width: 4px;
  margin-bottom: 1rem;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* Error Section */
.error-section {
  text-align: center;
  padding: 3rem;
  color: #e53e3e;
}

.retry-btn {
  background: #e53e3e;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  margin-top: 1rem;
}

/* Concepts Section */
.concepts-section {
  margin-top: 2rem;
}

.concepts-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.concepts-header h2 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #2d3748;
  margin: 0;
}

.refresh-btn {
  background: linear-gradient(135deg, #48bb78 0%, #38a169 100%);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s;
  box-shadow: 0 2px 4px rgba(72, 187, 120, 0.2);
}

.refresh-btn:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(72, 187, 120, 0.3);
}

.refresh-btn:disabled {
  background: #a0aec0;
  cursor: not-allowed;
  transform: none;
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 3rem;
  color: #718096;
  background: #f7fafc;
  border-radius: 12px;
  border: 2px dashed #e2e8f0;
}

.empty-state h3 {
  font-size: 1.3rem;
  margin-bottom: 0.5rem;
  color: #4a5568;
}

/* Concepts Grid */
.concepts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.concept-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border: 1px solid #e2e8f0;
}

.concept-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
}

.card-image {
  width: 100%;
  height: 200px;
  overflow: hidden;
  background: #f7fafc;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.concept-card:hover .card-image img {
  transform: scale(1.05);
}

.card-content {
  padding: 1.5rem;
}

.card-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 1rem;
  line-height: 1.3;
}

.card-details p {
  margin: 0.5rem 0;
  color: #718096;
  font-size: 0.9rem;
}

.card-details strong {
  color: #4a5568;
}

/* Responsive Design */
@media (max-width: 768px) {
  .concept-list-container {
    padding: 1rem;
  }
  
  .page-title {
    font-size: 2rem;
  }
  
  .form-section {
    padding: 1.5rem;
  }
  
  .form-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .concepts-header {
    flex-direction: column;
    align-items: stretch;
  }
  
  .concepts-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
}
</style>