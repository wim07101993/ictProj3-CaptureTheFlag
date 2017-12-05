<div class="modalAddCategory"
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
<div class="modal modalAddCategory" style="position:fixed; top:30vh;  z-index: 100 !important;">
    <div class="modal-content">
        <form class="addcategoryform" method="post" action="/categorieën/add">
        
        </form>
    </div>
    <div class="modal-footer">
        <a id="closeAdd" class="modal-action modal-close waves-effect waves-white red btn-flat" style="color:white;">Annuleer</a>
        <a id="saveAdd" class="modal-action modal-close waves-effect waves-white green accent-3 btn-flat" style="color:white;">Opslaan</a>
    </div>
</div>

<script>
function addAddListener(){
    $(".addcategory").click(function(){
        $(".modalAddCategory").fadeIn(1000);
        var generateView="";
        generateView+=csrf;
            generateView+="<h4>Categorie</h4>"
            generateView+="<input id='name' name='name' value='' required />";
        $(".addcategoryform").html(generateView)
    });
    $("#closeAdd").click(function(){
           $(".modalAddCategory").fadeOut(1000);
    })
    $("#saveAdd").click(function(){
        if(document.getElementById("name").value != ''){
            $(".addcategoryform").submit();
        } else {
            document.getElementById("name").className = "Invalid";
        }
    })
}
</script>