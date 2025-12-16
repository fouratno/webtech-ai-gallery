<template>
  <div id="app">
    <header>
      <h1>AI Interior Gallery</h1>
      <p class="subtitle">Create and browse curated AI-powered interior design prompts.</p>
    </header>
    <main>
      <ConceptList />
    </main>
  </div>
</template>

<script setup lang="ts">
import ConceptList from './components/ConceptList.vue'
</script>

<style>
body {
  font-family: 'Inter', sans-serif;
  background-color: #f5f5f7;
  margin: 0;
  padding: 0;
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  text-align: center;
}

header {
  margin-top: 32px;
  margin-bottom: 12px;
}

h1 {
  font-size: 2.2rem;
  font-weight: 700;
  color: #111;
  margin: 0 0 0.4rem;
}

.subtitle {
  margin: 0;
  color: #4f5660;
}

main {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 2rem;
  padding: 1rem;
}
</style>
