document.querySelector("#makeAdmin").onclick = function(){
  let td = document.getElementById('data').getElementsByTagName('td');
          let id = td[0].innerText;
          href="/mail/"+id;
          fetch(href, {
                method: "GET",
                headers: {
                "Content-Type": "application/json;charset=utf-8"
                },
          })
}

