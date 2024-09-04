import request from '@/utils/request';

const BASE_API = "/login_manger";

export default {
    page(query) {
        return request({
            url: BASE_API + "/page",
            method: "post",
            data: query
        });
    },
    get(id) {
        return request({
            url: BASE_API + "/get/" + id,
            method: "get"
        });
    },
    add(data) {
        return request({
            url: BASE_API + "/add",
            method: "post",
            data: data
        });
    }, update(data) {
        return request({
            url: BASE_API + "/update",
            method: "put",
            data: data
        });
    },
    delete(id) {
        return request({
            url: BASE_API + "/delete/" + id,
            method: "delete"
        });
    },
    relevance_all() {
        return request({
            url: BASE_API + "/relevance/all",
            method: "get"
        });
    },
    relevance_get_value(data) {
        return request({
            url: BASE_API + "/relevance/get_value",
            method: "get",
            params: data
        });
    },
    update_password(data){
        return request({
            url: BASE_API + "/update_password",
            method: "post",
            data: data
        });
    }
};