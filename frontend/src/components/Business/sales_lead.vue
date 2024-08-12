<template>
  <base-wrapper
>
<base-header>
<search-operation
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
firstSearchData="salesName"
>
<template #collapse>
    <el-form-item  label="销售线索名称" prop="salesName">
        <el-input placeholder="请输入销售线索名称" v-model="listQuery.salesName" />
    </el-form-item>

<el-form-item v-show="!props.params.userId" type="select" label="跟进人" prop="userId">
    <el-select
clearable
placeholder="请选择"
v-model="listQuery.userId"
>
<el-option
:label="item.label"
:value="item.value"
v-for="item in user_info_user_id_options"
:key="item.value"
>
</el-option>
</el-select>
</el-form-item>

    <el-form-item  label="销售线索的描述" prop="salesDescription">
        <el-input placeholder="请输入销售线索的描述" v-model="listQuery.salesDescription" />
    </el-form-item>

<el-form-item v-show="!props.params.salesStatus" type="select" label="销售线索状态" prop="salesStatus">
    <el-select
clearable
placeholder="请选择"
v-model="listQuery.salesStatus"
>
<el-option
:label="item.label"
:value="item.value"
v-for="item in sales_status_sales_status_id_options"
:key="item.value"
>
</el-option>
</el-select>
</el-form-item>

<el-form-item v-show="!props.params.partnerId" type="select" label="合作伙伴" prop="partnerId">
    <el-select
clearable
placeholder="请选择"
v-model="listQuery.partnerId"
>
<el-option
:label="item.label"
:value="item.value"
v-for="item in partner_partner_id_options"
:key="item.value"
>
</el-option>
</el-select>
</el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
ref="baseTableRef"
:params="listQuery"
api="sales_lead_all.page"
>
<el-table-column
prop="salesLeadId"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="salesName"
label="销售线索名称"
align="center"
>

</el-table-column>
<el-table-column
prop="userName"
label="跟进人"
align="center"
>

</el-table-column>
<el-table-column
prop="salesDescription"
label="销售线索的描述"
align="center"
>

</el-table-column>
<el-table-column
prop="salesStatusName"
label="销售线索状态"
align="center"
>

</el-table-column>
<el-table-column
prop="partnerName"
label="合作伙伴"
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
path="/sales_lead/detail"
:query="{id: scope.row.salesLeadId}"
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
:rules='{"salesName":[{"trigger":"blur","message":"销售线索名称不能为空","required":true}]}'
label-width='100px'
>
    <el-form-item  label="销售线索名称" prop="salesName">
        <el-input placeholder="请输入销售线索名称" v-model="form.salesName" />
    </el-form-item>

<el-form-item v-show="!props.params.userId" type="select" label="跟进人" prop="userId">
    <el-select
clearable
placeholder="请选择"
v-model="form.userId"
>
<el-option
:label="item.label"
:value="item.value"
v-for="item in user_info_user_id_options"
:key="item.value"
>
</el-option>
</el-select>
</el-form-item>

    <el-form-item  label="销售线索的描述" prop="salesDescription">
        <el-input placeholder="请输入销售线索的描述" v-model="form.salesDescription" />
    </el-form-item>

<el-form-item v-show="!props.params.salesStatus" type="select" label="销售线索状态" prop="salesStatus">
    <el-select
clearable
placeholder="请选择"
v-model="form.salesStatus"
>
<el-option
:label="item.label"
:value="item.value"
v-for="item in sales_status_sales_status_id_options"
:key="item.value"
>
</el-option>
</el-select>
</el-form-item>

<el-form-item v-show="!props.params.partnerId" type="select" label="合作伙伴" prop="partnerId">
    <el-select
clearable
placeholder="请选择"
v-model="form.partnerId"
>
<el-option
:label="item.label"
:value="item.value"
v-for="item in partner_partner_id_options"
:key="item.value"
>
</el-option>
</el-select>
</el-form-item>

</el-form>
<base-cell
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.salesLeadId }}
</base-cell-item>
<base-cell-item
label="销售线索名称"
>
{{ form.salesName }}
</base-cell-item>
<base-cell-item
label="跟进人"
>
{{ form.userId }}
</base-cell-item>
<base-cell-item
label="销售线索的描述"
>
{{ form.salesDescription }}
</base-cell-item>
<base-cell-item
label="销售线索状态"
>
{{ form.salesStatus }}
</base-cell-item>
<base-cell-item
label="合作伙伴"
>
{{ form.partnerId }}
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


let sales_status_sales_status_id_options=ref([])
get_sales_status_list()
let user_info_user_id_options=ref([])
get_user_info_list()
let partner_partner_id_options=ref([])
get_partner_list()




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
  let res = await proxy.$api.sales_lead.delete(row.salesLeadId);
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
          let res = await proxy.$api.sales_lead[dialogStatus.value](form.value);
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


async function get_sales_status_list() {
    let res = await proxy.$api.sales_lead_all['get_sales_status_list']();
    if (res.code == 0) {
    sales_status_sales_status_id_options.value = Object.assign({}, res.data);
    } else {
        proxy.$modal.msgError(res.message);
    }
}



async function get_user_info_list() {
    let res = await proxy.$api.sales_lead_all['get_user_info_list']();
    if (res.code == 0) {
    user_info_user_id_options.value = Object.assign({}, res.data);
    } else {
        proxy.$modal.msgError(res.message);
    }
}



async function get_partner_list() {
    let res = await proxy.$api.sales_lead_all['get_partner_list']();
    if (res.code == 0) {
    partner_partner_id_options.value = Object.assign({}, res.data);
    } else {
        proxy.$modal.msgError(res.message);
    }
}


</script>

<style lang="scss" scoped></style>