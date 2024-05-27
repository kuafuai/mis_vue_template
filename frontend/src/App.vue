<template>
  <pc v-if="type==='PC'" />
  <phone v-else />
  
</template>

<script setup>
import pc from '@/layout/pc.vue'
import phone from '@/layout/pc.vue'

const type = import.meta.env.VITE_APP_NAME;

</script>

<style scoped>

</style>
