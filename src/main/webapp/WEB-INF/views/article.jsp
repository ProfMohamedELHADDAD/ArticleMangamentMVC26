<%--
  Created by IntelliJ IDEA.
  User: elhaddadmohamed
  Date: 3/10/2025
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="ginf3.managearticle26.Model.Article" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>ARTICLES</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <style>
    body {
      color: #404E67;
      background: #F5F7FA;
      font-family: 'Open Sans', sans-serif;
    }
    .table-wrapper {
      width: 700px;
      margin: 30px auto;
      background: #fff;
      padding: 20px;
      box-shadow: 0 1px 1px rgba(0,0,0,.05);
    }
    .table-title {
      padding-bottom: 10px;
      margin: 0 0 10px;
    }
    .table-title h2 {
      margin: 6px 0 0;
      font-size: 22px;
    }
    .table-title .add-new {
      float: right;
      height: 30px;
      font-weight: bold;
      font-size: 12px;
      text-shadow: none;
      min-width: 100px;
      border-radius: 50px;
      line-height: 13px;
    }
    .table-title .add-new i {
      margin-right: 4px;
    }
    table.table {
      table-layout: fixed;
    }
    table.table tr th, table.table tr td {
      border-color: #e9e9e9;
    }
    table.table th i {
      font-size: 13px;
      margin: 0 5px;
      cursor: pointer;
    }
    table.table th:last-child {
      width: 100px;
    }
    table.table td a {
      cursor: pointer;
      display: inline-block;
      margin: 0 5px;
      min-width: 24px;
    }
    table.table td a.add {
      color: #27C46B;
    }
    table.table td a.edit {
      color: #FFC107;
    }
    table.table td a.delete {
      color: #E34724;
    }
    table.table td i {
      font-size: 19px;
    }
    table.table td a.add i {
      font-size: 24px;
      margin-right: -1px;
      position: relative;
      top: 3px;
    }
    table.table .form-control {
      height: 32px;
      line-height: 32px;
      box-shadow: none;
      border-radius: 2px;
    }
    table.table .form-control.error {
      border-color: #f50000;
    }
    table.table td .add {
      display: none;
    }
  </style>
  <script>
    $(document).ready(function(){
      $('[data-toggle="tooltip"]').tooltip();
      var actions = $("table td:last-child").html();
      // Append table with add row form on add new button click

      // Add row on add button click
      $(document).on("click", ".add", function(){
        debugger;
        let fields = [
          $("#code"),
          $("#designation"),
          $("#prix")
        ];
        console.log(fields);
        fields.forEach(field => {
          if (field.val() === "") {
            field.addClass("error");
            isValid = false;
          } else {
            field.removeClass("error");
          }
        });
        if (isValid) {
          $.ajax({
            url: "{{ path('${pageContext.request.contextPath}/articles/create') }}",
            method: 'POST',
            data: $(this).serialize()
          }).then((response) => {
            console.log(response)
          })
        }$

        /* let isValid = true;
         var empty = false;
         var input = $(this).parents("tr").find('input[type="text"]');
         input.each(function(){
             if(!$(this).val()){
                 $(this).addClass("error");
                 empty = true;
             } else{
                 $(this).removeClass("error");
             }
         });
         $(this).parents("tr").find(".error").first().focus();
         if(!empty){
             input.each(function(){
                 $(this).parent("td").html($(this).val());
             });
             $(this).parents("tr").find(".add, .edit").toggle();
             $(".add-new").removeAttr("disabled");
         }*/
      });
      // Edit row on edit button click
      $(document).on("click", ".edit", function(){
        document.getElementById('formEdit').action='${pageContext.request.contextPath}/articles/update?code='+$(this).parents("tr").find("td:not(:last-child)")[0].textContent
        document.getElementById('codeEdit').value=$(this).parents("tr").find("td:not(:last-child)")[0].textContent;
        document.getElementById('designationEdit').value=$(this).parents("tr").find("td:not(:last-child)")[1].textContent;
        document.getElementById('prixEdit').value=$(this).parents("tr").find("td:not(:last-child)")[2].textContent;
      });
      // Delete row on delete button click
      $(document).on("click", ".delete", function(){
        document.getElementById('formDelete').action='${pageContext.request.contextPath}/articles/delete?code='+$(this).parents("tr").find("td:not(:last-child)")[0].textContent;

      });
    });
  </script>
</head>
<body>
<div class="container-lg">
  <div class="table-responsive">
    <div class="table-wrapper">
      <div class="table-title">
        <div class="row">
          <div class="col-sm-8"><h2>ARTICLES <b>Details</b></h2></div>
          <div class="col-sm-4">
            <button type="button" data-toggle="modal" data-target="#ModalADD" class="btn btn-info add-new"><i class="fa fa-plus"></i> Add New</button>

            <!-- Modal Add Article-->
            <div class="modal fade" id="ModalADD" tabindex="-1" role="dialog" aria-labelledby="ModalADDLabel" aria-hidden="true">
              <form action="${pageContext.request.contextPath}/articles/create" method="post">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="ModalADDLabel">Add Article</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">

                      <label for="code">Code:</label>
                      <input type="text" class="form-control" name="code" id="code">
                      <br>
                      <label for="designation">Designation:</label>
                      <input type="text" class="form-control" name="designation" id="designation">
                      <br>
                      <label for="prix">Prix:</label>
                      <input type="text" class="form-control" name="prix" id="prix">


                    </div>
                    <div class="modal-footer">

                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                      <input type="submit" class="btn btn-primary"  value="Save">
                    </div>
                  </div>
                </div>
              </form>
            </div>

          </div>
        </div>
      </div>
      <!-- Modal Delete Article-->
      <div class="modal fade" id="ModalDelete" tabindex="-1" role="dialog" aria-labelledby="ModalDeleteLabel" aria-hidden="true">
        <form action="${pageContext.request.contextPath}/articles/delete" id="formDelete" method="post">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="ModalDeleteLabel">Delete Article</h5>"+
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">

                <label >Etes vous sur de la suppression ?</label>


              </div>
              <div class="modal-footer">

                <button type="button" class="btn btn-secondary" data-dismiss="modal">Non</button>
                <input type="submit" class="btn btn-primary"  value="Oui">
              </div>
            </div>
          </div>
        </form>
      </div>
      <!-- Modal Edit Article-->
      <div class="modal fade" id="ModalEdit" tabindex="-1" role="dialog" aria-labelledby="ModalEditLabel" aria-hidden="true">
        <form id="formEdit" action="${pageContext.request.contextPath}/articles/update" method="post">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="ModalEditLabel">Edit Article</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">

                <label for="code">Code:</label>
                <input type="text" class="form-control" readonly name="code" id="codeEdit">
                <br>
                <label for="designation">Designation:</label>
                <input type="text" class="form-control" name="designation" id="designationEdit">
                <br>
                <label for="prix">Prix:</label>
                <input type="text" class="form-control" name="prix" id="prixEdit">


              </div>
              <div class="modal-footer">

                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <input type="submit" class="btn btn-primary"  value="Save">
              </div>
            </div>
          </div>
        </form>
      </div>


      <%List<Article> Maliste= (List<Article>)request.getAttribute("articles");%>
      <table class="table table-bordered">
        <thead>
        <tr>
          <th>CODE</th>
          <th>DESIGNATION</th>
          <th>PRIX</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Article article: Maliste) { %>
        <tr>
          <td><%=article.getCode()%></td>
          <td><%=article.getDesignation()%></td>
          <td><%=article.getPrix()%></td>
          <td>
            <a class="add" title="Add"  data-toggle="tooltip">
              <i class="material-icons">&#xE03B;</i></a>
            <a class="edit" title="Edit" data-toggle="modal" data-target="#ModalEdit">
              <i class="material-icons">&#xE254;</i></a>
            <a class="delete" title="Delete" data-toggle="modal" data-target="#ModalDelete" >
              <i class="material-icons">&#xE872;</i></a>
          </td>
        </tr>
        <% }%>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>
