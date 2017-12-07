<div class="modalEditCategories"
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
<div class="modal modalEditCategories" style="position:fixed; top:30vh; z-index: 100 !important;">
    <div class="modal-content">
        <form class="editcategoriesform" method="post" action="/vragen/edit">
                
        </form>
    </div>
    <div class="modal-footer">
        <a id="closeCategorieën" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="saveCategorieën" class="modal-action modal-close waves-effect waves-white green btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
 function addEditCategoryListener(){
    $(".editcategories").click(function(){
        var id = this.id;
        var vraag = vragen.filter(function(vraag){
            return vraag.Question_ID == id;
        });
        vraag=vraag[0]
        console.log(vraag);
        $(".modalEditCategories").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
        generateView+="<h4>Categorieën voor: </h4> <h5>"+ vraag.Question + "</h5>"; 
        generateView+="<input name='id' value='"+vraag.Question_ID+"' style='display:none;'/>";
        
        categorieën.forEach(function(categorie){
            var checked = "";
            vraag["Categories"].forEach(function(vraagCategorie){
                if(vraagCategorie.Category_ID == categorie.Category_ID){
                    checked = "checked = 'checked'";
                }
            });

            generateView+='<p><input type="checkbox" id="#'+ categorie.Category_ID + '" ' + checked +'" /><label for="#'+ categorie.Category_ID +'">'+ categorie.Name +'</label></p>';
        });

        $(".editcategoriesform").html(generateView)
    });
    $("#closeCategorieën").click(function(){
           $(".modalEditCategories").fadeOut(1000);
    })
    $("#saveCategorieën").click(function(){
        if(document.getElementById("editCategory").value != ''){
            $(".editcategoriesform").submit();
        } else {
            document.getElementById("editCategory").className = "Invalid";
        }
    })
}
</script>