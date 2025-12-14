import { describe, it, expect, beforeEach, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import ConceptList from '../ConceptList.vue'

// Mock fetch
global.fetch = vi.fn()

describe('ConceptList', () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it('renders properly', () => {
    const wrapper = mount(ConceptList)
    expect(wrapper.text()).toContain('AI Interior Gallery')
    expect(wrapper.text()).toContain('Add New Concept')
  })

  it('displays form fields', () => {
    const wrapper = mount(ConceptList)
    
    expect(wrapper.find('#title').exists()).toBe(true)
    expect(wrapper.find('#promptArtist').exists()).toBe(true)
    expect(wrapper.find('#aiTool').exists()).toBe(true)
    expect(wrapper.find('#imageUrl').exists()).toBe(true)
  })

  it('shows loading state initially', async () => {
    // Mock fetch to return a pending promise
    const mockFetch = vi.fn(() => new Promise(() => {}))
    global.fetch = mockFetch

    const wrapper = mount(ConceptList)
    
    // Wait for component to mount
    await wrapper.vm.$nextTick()
    
    expect(wrapper.text()).toContain('Loading concepts...')
  })

  it('displays concepts when loaded', async () => {
    const mockConcepts = [
      {
        id: 1,
        title: 'Modern Living Room',
        promptArtist: 'John Doe',
        aiTool: 'DALL-E',
        imageUrl: 'https://example.com/image1.jpg'
      },
      {
        id: 2,
        title: 'Cozy Bedroom',
        promptArtist: 'Jane Smith',
        aiTool: 'Midjourney',
        imageUrl: 'https://example.com/image2.jpg'
      }
    ]

    const mockFetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockConcepts)
      })
    )
    global.fetch = mockFetch

    const wrapper = mount(ConceptList)
    
    // Wait for async operations
    await new Promise(resolve => setTimeout(resolve, 0))
    await wrapper.vm.$nextTick()

    expect(wrapper.text()).toContain('Modern Living Room')
    expect(wrapper.text()).toContain('Cozy Bedroom')
    expect(wrapper.text()).toContain('Gallery (2 concepts)')
  })

  it('shows empty state when no concepts', async () => {
    const mockFetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve([])
      })
    )
    global.fetch = mockFetch

    const wrapper = mount(ConceptList)
    
    // Wait for async operations
    await new Promise(resolve => setTimeout(resolve, 0))
    await wrapper.vm.$nextTick()

    expect(wrapper.text()).toContain('No concepts yet')
    expect(wrapper.text()).toContain('Be the first to add an AI interior design concept!')
  })

  it('validates required title field', async () => {
    const wrapper = mount(ConceptList)
    
    // Try to submit form without title
    const form = wrapper.find('form')
    await form.trigger('submit.prevent')
    
    expect(wrapper.text()).toContain('Title is required')
  })

  it('validates minimum title length', async () => {
    const wrapper = mount(ConceptList)
    
    // Set a title that's too short
    const titleInput = wrapper.find('#title')
    await titleInput.setValue('ab')
    
    const form = wrapper.find('form')
    await form.trigger('submit.prevent')
    
    expect(wrapper.text()).toContain('Title must be at least 3 characters')
  })
})