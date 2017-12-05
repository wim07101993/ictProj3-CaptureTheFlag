<script>
    var changed = false;
    $("#search").on('change keydown paste input', function(){
        let trefwoord = $("#search").val();
        filteredVragen = vragen.filter(function(vraag){
            if(vraag["Question"].toLowerCase().indexOf(trefwoord.toLowerCase())!=-1||(vraag["Question_ID"]+"").indexOf(trefwoord)!=-1){
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
        for(vraag of filteredVragen){
            TableHtml+="<tr>";
            TableHtml+="<td>"+vraag["Question_ID"]+"</td>";
            TableHtml+="<td>"+vraag["Question"]+"</td>";
            TableHtml+='<td><a class="waves-effect waves-light btn amber accent-3 editanwsers" href="antwoorden/'+vraag["Question_ID"]+'">Antwoorden</a></td>';
            TableHtml+=' <td><a class="waves-effect waves-light btn green accent-3 editquestion" id="'+vraag["Question_ID"]+'">Bewerk</a></td>';
            TableHtml+='<td><a class="waves-effect waves-light btn red deletequestion" id="'+vraag["Question_ID"]+'">Verwijder</a></td>';
        }
        TableHtml+=' <tr class="grey lighten-3" style="border:none;"><td>';                 
        TableHtml+='<a class="btn-floating btn-large waves-effect waves-light green accent-3 addquestion"><i class="material-icons">add</i></a>';
        TableHtml+='</td></tr>';
        $("#table").html(TableHtml);
        addEditListeners();
        addAddListener();
        addDeleteListener();
    }
    generateVragenTabel();
</script>