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
const {proxy} = getCurrentInstance();
// Importing to define props and emit
import {defineProps, defineEmits} from 'vue';
import {ElMessage} from 'element-plus';
// Defining props to receive form and rules from parent
const props = defineProps({
  form: {
    type: Object,
    required: false
  },
  rules: {
    type: Object,
    required: false
  },
  table_module: {
    type: String,
    required: false
  }

});

// Emit to notify parent of form submission
const emit = defineEmits(['success', "fail"]);
// Handle form submission
const handleSubmit = async () => {
  try {
    let res = await proxy.$api[props.table_module]["add"](props.form);
    console.log(res.data)
    emit("success")
  } catch (e) {
    console.log(e)
    emit("fail")
  }
}
// Defining the submit function
const onSubmit = () => {
  // Use the form reference to validate the form
  proxy.$refs.formRef.validate((valid) => {
    if (valid) {
      handleSubmit()
      // Emitting the 'submit' event with form data
    } else {
      return false;
    }
  });
};

// Defining the reset function
const onResetForm = () => {
  formRef.value.resetFields();
  ElMessage.info('Form reset successfully!');
};
</script>

<style scoped>
/* Add any custom styles for your form here */
</style>
