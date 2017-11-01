var modelHeader = [{col:'id'},{col:'名称'},{col:'关键字(key)'},{col:'描述'},{col:'版本'},{col:'操作'}];
var processesHeader = [{col:'id'},{col:'名称'},{col:'关键字(key)'},{col:'描述'},{col:'版本'},{col:'操作'}];
var taskHeader = [{col:'id'},{col:'名称'},{col:'描述'},{col:'变量'},{col:'操作'}];

var baseUrl = "http://localhost:10000/";
var model_list_url = baseUrl+"/models/list";
var model_deploy_url = baseUrl+"models/deploy/";
var model_editor_url = baseUrl+"/modeler.html?modelId=";
var process_list_url = baseUrl+"/processes/list";
var process_form_url = baseUrl+"/processes/pre-start/"
var task_list_url = baseUrl+"/task/list";
var task_form_url = baseUrl+"/task/form?taskId=";
var task_exec_url = baseUrl+"/task/exec/";

function request(url,_function) {
    $.ajax({
        url: url,
        data: null,
        success: _function,
        dataType: "json"
    });
}

function deploy(_data) {
    $.ajax({
       url: model_deploy_url+''+_data.name,
       success:function () {
           alert("部署成功！");
       },
       error:function (error) {
           alert(error.toString());
       }
    });
}

function editor(_data) {
    location.href = model_editor_url+_data.name;
}

function startProcess(_data) {
    dynamicForm.dynamicId(_data.name);
    $.post(process_form_url+_data.name,null,function (data) {
        dynamicForm.dynamicId(_data.name);
        dynamicForm.dynamicPro(data);
        $('#startModal').modal('show')
    });
}

