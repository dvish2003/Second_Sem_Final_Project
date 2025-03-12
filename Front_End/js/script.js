getAllEmployee();
function clearTextField(){
    $('#empID').val("");
    $('#empName').val("");
    $('#empAddress').val("");
    $('#empMNumber').val("");
}
function addEmployee(){
    let name = $('#empName').val();
    let address = $('#empAddress').val();
    let mobile = $('#empMNumber').val();
    console.log(name, address, mobile);

    $.ajax({
        method: 'POST',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/v1/employee/saveEmployee',
        async: true,
        data: JSON.stringify({
            "empID": null,
            "empName": name,
            "empAddress": address,
            "empMNumber": mobile
        }),
        success: function(data) {
            getAllEmployee();
            alert("Employee added successfully");
            $('#empID').val("");
            $('#empName').val("");
            $('#empAddress').val("");
            $('#empMNumber').val("");
        },
        error: function(error) {
            console.error(error);
            alert("Error while adding employee");
            $('#empID').val("");
            $('#empName').val("");
            $('#empAddress').val("");
            $('#empMNumber').val("");
        }
    });
}

function updateEmployee(){
    let empID = $('#empID').val();
    let name = $('#empName').val();
    let address = $('#empAddress').val();
    let mobile = $('#empMNumber').val();
    console.log(name, address, mobile);

    $.ajax({
        method: 'PUT',
        contentType: 'application/json',
        url: 'http://localhost:8080/api/v1/employee/updateEmployee',
        async: true,
        data: JSON.stringify({
            "empID": empID,
            "empName": name,
            "empAddress": address,
            "empMNumber": mobile
        }),
        success: function(data) {
            getAllEmployee();
            alert("Employee update successfully");
            $('#empID').val("");
            $('#empName').val("");
            $('#empAddress').val("");
            $('#empMNumber').val("");
        },
        error: function(error) {
            console.error(error);
            alert("Error while Update employee");
            $('#empID').val("");
            $('#empName').val("");
            $('#empAddress').val("");
            $('#empMNumber').val("");
        }
    });
}

function deleteEmployee(){
    let empID = $('#empID').val();

    $.ajax({
        method: 'DELETE',
        url: 'http://localhost:8080/api/v1/employee/deleteEmployee/'+empID,
        async: true,
        success: function(data) {
            getAllEmployee();
            alert("Employee delete successfully");
            $('#empID').val("");
            $('#empName').val("");
            $('#empAddress').val("");
            $('#empMNumber').val("");
        },
        error: function(error) {
            console.error(error);
            alert("Error while delete employee");
            $('#empID').val("");
            $('#empName').val("");
            $('#empAddress').val("");
            $('#empMNumber').val("");
        }
    });
}
function getAllEmployee() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/api/v1/employee/getAllEmployees',
        async: true,
        success: function(response) {
            console.log(response);
            if (response.code === "00") {
                $('#employeeBody').empty();
                response.content.forEach(emp => {
                    $('#employeeBody').append(`
              <tr>
                <td>${emp.empID}</td>
                <td>${emp.empName}</td>
                <td>${emp.empAddress}</td>
                <td>${emp.empMNumber}</td>
              </tr>
            `);
                });
            }
        },
        error: function(error) {
            console.error(error);
            alert("Error while fetching employee data");
        }
    });
}
$(document).ready(function() {
    $(document).on('click','#employeeTable tr', function(){
        let empID = $(this).find('td:eq(0)').text();
        let empName = $(this).find('td:eq(1)').text();
        let empAddress = $(this).find('td:eq(2)').text();
        let empMNumber = $(this).find('td:eq(3)').text();

        $('#empID').val(empID);
        $('#empName').val(empName);
        $('#empAddress').val(empAddress);
        $('#empMNumber').val(empMNumber);

    })
})
