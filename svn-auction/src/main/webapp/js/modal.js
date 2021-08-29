function insertAfter(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}


var imgs // array of imgs
,	i // counter over images
,	m // newly created div for modal
,	s // newly created span
,	img // newly created image for modal
,	c; // newly created div for caption


imgs = document.getElementsByTagName("img");
for (i = 0; i < imgs.length; i++) {

    m = document.createElement("div");
    m.setAttribute("class", "modal");

    s = document.createElement("span");
    s.setAttribute("class", "close");
    s.innerHTML = "&times;";

    img = document.createElement("img");
    img.setAttribute("class", "modal-content");

    c = document.createElement("div");
    c.setAttribute("class", "caption");

    imgs[i].onclick = function() {
        m.style.display = "block";
        img.src = this.src;
        c.innerHTML = this.alt;
    }

    s.onclick = function() {
        m.style.display = "none";
    }

    m.appendChild(s);
    m.appendChild(img);
    m.appendChild(c);


    insertAfter(imgs[i], m);
    i++;
}