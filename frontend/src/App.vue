<template>
  <pc_new v-if="type==='PC'" />
  <phone v-else />
  
</template>

<script setup>
import pc from '@/layout/pc.vue'
import pc_new from '@/layout/pc_new.vue'
import phone from '@/layout/phone.vue'

const type = import.meta.env.VITE_APP_TYPE;

</script>

<style scoped>

</style>
