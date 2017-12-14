<script>
    var changed = false;
    $("#search").on('change keydown paste input', function(){
        let trefwoord = $("#search").val();
        filteredCategorieën = categorieën.filter(function(categorie){
            if(categorie["Name"].toLowerCase().indexOf(trefwoord.toLowerCase())!=-1||(categorie["Category_ID"]+"").indexOf(trefwoord)!=-1){
                return true;
            }
            return false;
        });
        changed=true;
    });
    setInterval(function(){
        if(changed){
            generateVragenTabel();
            changed=false;
        }
    },500);
</script>
<script>
    function generateVragenTabel(){
        var TableHtml="";
        for(categorie of filteredCategorieën){
            var disabled = "";

            if(categorie.Category_ID == 1){
                disabled = "disabled";
            }
            TableHtml+="<tr>";
            TableHtml+="<td>"+categorie["Name"]+"</td>";

            var aantalVragenClass = 'class="red"';
            if(categorie["Questions"].length != 0){ 
                aantalVragenClass = 'class="green"'
            }
            TableHtml+='<td><span '+ aantalVragenClass + 'style="color:white; border-radius:2px;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.14), 0 1px 5px 0 rgba(0,0,0,0.12), 0 3px 1px -2px rgba(0,0,0,0.2);">&nbsp;'+categorie["Questions"].length +'&nbsp;</span></td>';
            TableHtml+=' <td><a class="waves-effect waves-light btn green editcategory '+ disabled +'" id="'+categorie["Category_ID"]+'">Bewerk</a></td>';
            TableHtml+='<td><a class="waves-effect waves-light btn red deletecategory '+ disabled +'" id="'+categorie["Category_ID"]+'">Verwijder</a></td>';
        }
        TableHtml+=' <tr class="grey lighten-3" style="border:none;"><td>';                 
        TableHtml+='<a class="btn-floating btn-large waves-effect waves-light green addcategory"><i class="material-icons">add</i></a>';
        TableHtml+='</td></tr>';
        $("#table").html(TableHtml);
        addEditListeners();
        addAddListener();
        addDeleteListener();
    }
    generateVragenTabel();
</script>