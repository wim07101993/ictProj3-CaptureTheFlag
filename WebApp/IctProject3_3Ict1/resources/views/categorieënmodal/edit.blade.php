<div class="modalEditCategory"
    style="background: black;
           background-color: #00000070;
           position: fixed;
           top: 0;
           bottom: 0;
           left: 0;
           right: 0;
           z-index: 1;
           display:none;" >
</div>
<div class="modal modalEditCategory" style="position:fixed; top:30vh; z-index: 100 !important;">
    <div class="modal-content">
        <form class="editcategoryform" method="post" action="/categorieën/edit">  
            
        </form>
    </div>
    <div class="modal-footer">
        <a id="close" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="save" class="modal-action modal-close waves-effect waves-white green btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
function addEditListeners(){
    $(".editcategory").click(function(){
        var id = this.id;
        var categorie =categorieën.filter(function(categorie){
            return categorie.Category_ID == id;
        });
        categorie=categorie[0]
        $(".modalEditCategory").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
            generateView+="<input name='id' value='"+categorie.Category_ID+"' style='display:none;'/>";
            generateView+="<h4>Categorie</h4>"; 
            generateView+="<input id='editName' name='name' value='"+categorie.Name+"' />";
        $(".editcategoryform").html(generateView)
    });
    $("#close").click(function(){
           $(".modalEditCategory").fadeOut(1000);
    })
    $("#save").click(function(){
        if(document.getElementById("editName").value != ''){
            $(".editcategoryform").submit();
        } else {
            document.getElementById("editName").className = "invalid";
        }
    })
}
</script>