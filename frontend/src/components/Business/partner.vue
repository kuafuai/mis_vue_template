<template>
  <base-wrapper
>
<base-header>
<search-operation
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
firstSearchData="partnerWechatLoginId"
>
<template #collapse>
    <el-form-item  label="微信绑定ID" prop="partnerWechatLoginId">
        <el-input placeholder="请输入微信绑定ID" v-model="listQuery.partnerWechatLoginId" />
    </el-form-item>

    <el-form-item  label="手机号" prop="partnerPhoneNumber">
        <el-input placeholder="请输入手机号" v-model="listQuery.partnerPhoneNumber" />
    </el-form-item>

    <el-form-item  label="密码" prop="partnerPassword">
        <el-input placeholder="请输入密码" v-model="listQuery.partnerPassword" />
    </el-form-item>

    <el-form-item  label="姓名" prop="partnerName">
        <el-input placeholder="请输入姓名" v-model="listQuery.partnerName" />
    </el-form-item>

    <el-form-item  label="身份证号" prop="idCard">
        <el-input placeholder="请输入身份证号" v-model="listQuery.idCard" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
ref="baseTableRef"
:params="listQuery"
api="partner.page"
>
<el-table-column
prop="partnerId"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="partnerWechatLoginId"
label="微信绑定ID"
align="center"
>

</el-table-column>
<el-table-column
prop="partnerPhoneNumber"
label="手机号"
align="center"
>

</el-table-column>
<el-table-column
prop="partnerPassword"
label="密码"
align="center"
>

</el-table-column>
<el-table-column
prop="partnerName"
label="姓名"
align="center"
>

</el-table-column>
<el-table-column
prop="idCard"
label="身份证号"
align="center"
>

</el-table-column>
<el-table-column
width="220"
fixed="right"
label="操作"
align="center"
>
<template #default="scope">
<base-info-btn
path="/partner/detail"
:query="{id: scope.row.partnerId}"
text="详情"
>
</base-info-btn>
<base-edit-btn
@ok="handleUpdate(scope.row)"
>
</base-edit-btn>
<base-delete-btn
@ok="handleDelete(scope.row)"
></base-delete-btn>
</template>
</el-table-column>
</base-table-p>
<base-dialog
:title="dialogTitleObj[dialogStatus]"
width="50%"
style="max-width: 600px;"
v-model="dialogVisible"
>
<el-form
ref='dataFormRef'
v-if='dialogStatus !== "detail"'
:model='form'
:rules='{"partnerName":[{"trigger":"blur","message":"姓名不能为空","required":true}],"partnerPhoneNumber":[{"trigger":"blur","message":"手机号不能为空","required":true}],"idCard":[{"trigger":"blur","message":"身份证号不能为空","required":true}],"partnerPassword":[{"trigger":"blur","message":"密码不能为空","required":true}],"partnerWechatLoginId":[{"trigger":"blur","message":"微信绑定ID不能为空","required":true}]}'
label-width='100px'
>
    <el-form-item  label="微信绑定ID" prop="partnerWechatLoginId">
        <el-input placeholder="请输入微信绑定ID" v-model="form.partnerWechatLoginId" />
    </el-form-item>

    <el-form-item  label="手机号" prop="partnerPhoneNumber">
        <el-input placeholder="请输入手机号" v-model="form.partnerPhoneNumber" />
    </el-form-item>

    <el-form-item  label="密码" prop="partnerPassword">
        <el-input placeholder="请输入密码" v-model="form.partnerPassword" />
    </el-form-item>

    <el-form-item  label="姓名" prop="partnerName">
        <el-input placeholder="请输入姓名" v-model="form.partnerName" />
    </el-form-item>

    <el-form-item  label="身份证号" prop="idCard">
        <el-input placeholder="请输入身份证号" v-model="form.idCard" />
    </el-form-item>

</el-form>
<base-cell
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.partnerId }}
</base-cell-item>
<base-cell-item
label="微信绑定ID"
>
{{ form.partnerWechatLoginId }}
</base-cell-item>
<base-cell-item
label="手机号"
>
{{ form.partnerPhoneNumber }}
</base-cell-item>
<base-cell-item
label="密码"
>
{{ form.partnerPassword }}
</base-cell-item>
<base-cell-item
label="姓名"
>
{{ form.partnerName }}
</base-cell-item>
<base-cell-item
label="身份证号"
>
{{ form.idCard }}
</base-cell-item>
</base-cell>
<template #footer>
<el-button
@click="dialogVisible = false"
round
>取 消</el-button>
<el-button
@click="submitForm"
round
color="#5D5FEF"
type="primary"
>确 定</el-button>
</template>
</base-dialog>
</base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();

const props = defineProps({
    params: { type: Object, default: () => ({}) },
})

// 计算属性，用于判断 params 的长度
const paramsLength = computed(() =>  Object.keys(props.params).length)

// 计算属性，判断 params 是否为空
const isParamsEmpty = computed(() => paramsLength.value === 0)

let listQuery = ref({});
let form = ref({});
let dialogVisible = ref(false);
let dialogStatus = ref('');
let dialogTitleObj = ref({ update: '编辑', add: '添加', detail: '详情'});
let rules = ref({});
if(isParamsEmpty.value){
  let routerQuery = proxy.$route.query;
  Object.assign(listQuery.value, routerQuery);
}
else{
  Object.assign(listQuery.value, props.params);
}





function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleDetail(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'detail';
  dialogVisible.value = true;
}
function handleAdd() {
  form.value = { ...props.params };
  dialogStatus.value = 'add';
  dialogVisible.value = true;
}
function handleUpdate(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.partner.delete(row.partnerId);
  refreshTableData();
  proxy.$modal.msgSuccess(res.message);
}
function submitForm() {
  if(dialogStatus.value == 'detail'){
      dialogVisible.value = false;
  }
  else {
      proxy.$refs.dataFormRef.validate(async (valid) => {
        if (valid) {
          let res = await proxy.$api.partner[dialogStatus.value](form.value);
          proxy.$modal.msgSuccess(res.message);
          refreshTableData();
          dialogVisible.value = false;
        }
      });
  }
}

function choose(item){
    form.value = Object.assign({}, item);
    dialogVisible.value = true;
    dialogStatus.value = 'update';
}

</script>

<style lang="scss" scoped></style>