<template>
<div class="login">
      <h2>登录</h2>
      <el-form
        ref="formRef"
        style="max-width: 320px"
        maxlength="10"
        :model="form"
        :rules="rules"
        label-width="auto"
        class="demo-form"
        status-icon
      >
        <el-form-item prop="phone">
          <el-input placeholder="请输入用户名" v-model="form.phone" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            type="password"
            v-model="form.password"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(formRef)">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script setup>
const { proxy } = getCurrentInstance();

// 定义事件
const emit = defineEmits(["loginSucceess"]);

/**
 * 数据
 */
 const form = reactive({
  name: "",
  phone: "admin",
  password: "123456",
});

const formRef = ref();

const rules = reactive({
  phone: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 1, max: 10, message: "用户名长度1-15位", trigger: "blur" },
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur",
    },
    { min: 6, max: 18, message: "密码长度", trigger: "blur" },
  ],
});

//  登录
const submitForm = async (formEl) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      proxy.$api.login.login(form).then((res)=> {
        
        localStorage.setItem("token", res.data);
        proxy.$modal.msgSuccess("登录成功");

        emit("loginSucceess");

      });
    } else {
      console.log("error submit!", fields);
    }
  });
};

</script>

<style lang="scss" scoped>
.login {
  width: 440px;
  height: 464px;
  background: #ffffff;
  border-radius: 6px;
  h2 {
    text-align: center;
    margin: 60px 163px 36px 163px;
  }
  .el-form {
    margin: 0 auto;
    margin-top: 72px;
    height: 264px;
    .el-form-item,
    .el-input__wrapper,
    .el-input {
      height: 40px;
      .el-input__inner {
        height: 100%;
      }
    }
    .el-button {
      width: 100%;
      height: 40px;
      margin-top: 22px;
    }
  }
}
</style>