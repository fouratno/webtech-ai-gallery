<template>
  <div id="app">
    <header>
      <h1>welcome to e inspiration</h1>
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

/* Centered container */
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  text-align: center;
}

/* Clean page header */
header {
  margin-top: 40px;
  margin-bottom: 20px;
}

h1 {
  font-size: 2rem;
  font-weight: 700;
  color: #111;
}

/* Layout styling for cards (inherited by ConceptList) */
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
