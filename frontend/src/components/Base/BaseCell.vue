<template>
  <div v-bind="$attrs">
    <div class="text-center bg-color-lightgrey p-y-10 " style="border: 1px solid rgb(230, 233, 240);">
      <span>{{ header }}</span>
    </div>
    <slot />
  </div>
</template>
<script setup>
const props = defineProps({
  labelWidth: {
    type: String,
    default: '200px',
  },
  header: {
    type: String,
    default: '详情'
  }
});
provide('baseCellLabelWidth', props.labelWidth);
</script>
<style lang="scss" scoped></style>
