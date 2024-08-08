<template>
  <el-form ref="formRef" :model="form" :rules="rules" label-width="auto" style="max-width: 600px"
           @submit.prevent="onSubmit">
    <slot name="form-items"></slot>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button @click="onResetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
// Importing to define props and emit
import {defineProps, defineEmits} from 'vue';
import {ElMessage} from 'element-plus';

// Defining props to receive form and rules from parent
const props = defineProps({
  form: {
    type: Object,
    required: true
  },
  rules: {
    type: Object,
    required: true
  }
});

// Emit to notify parent of form submission
const emit = defineEmits(['submit']);

// Defining the submit function
const onSubmit = () => {
  // Use the form reference to validate the form
  formRef.value.validate((valid) => {
    if (valid) {
      ElMessage.success('Form submitted successfully!');
      try {
        emit('submit', props.form);
        //  成功的方法
      } catch (e) {
        //  提交失败的方法
      }
      // Emitting the 'submit' event with form data
    } else {
      ElMessage.error('Please correct the errors in the form');
      return false;
    }
  });
};

// Defining the reset function
const onResetForm = () => {
  formRef.value.resetFields();
  ElMessage.info('Form reset successfully!');
};

// Define a ref for the form to access its methods
import {ref} from 'vue';

const formRef = ref(null);
</script>

<style scoped>
/* Add any custom styles for your form here */
</style>
