import { describe, it, expect, vi, afterEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import App from '../App.vue'
import ConceptList from '../components/ConceptList.vue'

afterEach(() => {
  vi.restoreAllMocks()
})

describe('App shell', () => {
  it('renders the gallery heading', () => {
    const wrapper = mount(App)
    expect(wrapper.text()).toContain('AI Interior Gallery')
    expect(wrapper.text()).toContain(
      'Create and browse curated AI-powered interior design prompts.'
    )
  })
})

describe('ConceptList', () => {
  const mockConcepts = [
    {
      id: 1,
      title: 'Minimal Loft',
      promptArtist: 'Studio A',
      aiTool: 'Midjourney',
      imageUrl: 'https://example.com/loft.jpg'
    }
  ]

  it('loads concepts from the API', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: async () => mockConcepts
      } as any)
    )

    const wrapper = mount(ConceptList)
    await flushPromises()

    expect(global.fetch).toHaveBeenCalledWith(expect.stringContaining('/concepts'))
    expect(wrapper.text()).toContain('Minimal Loft')
    expect(wrapper.text()).toContain('Studio A')
  })

  it('validates and submits a new concept', async () => {
    const createdConcept = {
      id: 2,
      title: 'Calm Bedroom',
      promptArtist: 'Luna',
      aiTool: 'SDXL',
      imageUrl: 'https://example.com/bedroom.jpg'
    }

    vi.stubGlobal(
      'fetch',
      vi
        .fn()
        .mockResolvedValueOnce({
          ok: true,
          json: async () => mockConcepts
        } as any)
        .mockResolvedValueOnce({
          ok: true,
          json: async () => createdConcept
        } as any)
    )

    const wrapper = mount(ConceptList)
    await flushPromises()

    const inputs = wrapper.findAll('input')
    expect(inputs.length).toBeGreaterThanOrEqual(4)

    await inputs[0]!.setValue(createdConcept.title)
    await inputs[1]!.setValue(createdConcept.promptArtist)
    await inputs[2]!.setValue(createdConcept.aiTool)
    await inputs[3]!.setValue(createdConcept.imageUrl)

    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(wrapper.text()).toContain('Concept created successfully')

    const cards = wrapper.findAll('.card')
    expect(cards.length).toBeGreaterThan(0)
    expect(cards[0]!.text()).toContain('Calm Bedroom')
  })

  it('renders create button', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: async () => []
      } as any)
    )

    const wrapper = mount(ConceptList)
    await flushPromises()

    expect(wrapper.find('button[type="submit"]').text()).toBe('Create Concept')
  })
})
