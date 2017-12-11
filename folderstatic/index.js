var searchBar = document.getElementById("search-bar");
var label = document.getElementById("label");
var showResult = document.getElementsByClassName("result")[0];

searchBar.addEventListener("focus", function(){
    label.style.animation = "move 1s forwards";
})

searchBar.addEventListener("keydown", function(e) {

    if(e.keyCode === 13){
        var q = searchBar.value;
        console.log(q);
        var page = 1;
        fetch(`/search`, {
            method: 'post',
            headers: {
                "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: `q=${q}&&page=${page}`
        })
        .then( success => {
            return success.json();
        })
        .then( result => {
            console.log(result);
            showResult.innerHTML = '';

            result.hits.forEach(function(hit){
                var str = ` <div class="item">
                            <a href="${hit.url}"><h2>${hit.title}</h2></a>
                            <a href="${hit.url}">${hit.url}</a>
                            <p>${hit.content}</p>
                        </div>`;
                showResult.innerHTML += str;
                showResult.innerHTML += `<div class="pagination">
                                            <a href="#">&laquo;</a>
                                            <a href="#">1</a>
                                            <a href="#">2</a>
                                            <a href="#">3</a>
                                            <a href="#">4</a>
                                            <a href="#">5</a>
                                            <a href="#">6</a>
                                            <a href="#">&raquo;</a>
                                        </div>`;
            })

        })
        .catch( err => alert(err))
    }
})
