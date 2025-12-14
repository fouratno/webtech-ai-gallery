<template>
  <div id="app">
    <ConceptList />
  </div>
</template>

<script>
import ConceptList from './components/ConceptList.vue'

export default {
  name: 'App',
  components: {
    ConceptList
  }
}
</script>

<style>
#app {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
</style>