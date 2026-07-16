/**/
document.addEventListener("DOMContentLoaded", function () {

    caricaRecensioni();

    const form = document.getElementById("form-recensione");

    if (form !== null) {
        form.addEventListener("submit", inserisciRecensione);
    }
});

async function caricaRecensioni() {

    const contenitore =
        document.getElementById("contenitore-recensioni");

    if (contenitore === null) {
        return;
    }

    try {
        const risposta = await fetch("/api/recensioni");

        if (!risposta.ok) {
            throw new Error("Servizio recensioni non ancora disponibile");
        }

        const recensioni = await risposta.json();

        if (recensioni.length === 0) {
            contenitore.innerHTML = `
                <div class="carousel-item active text-center">
                    <p class="text-muted mb-0">
                        Non sono ancora presenti recensioni.
                    </p>
                </div>
            `;

            return;
        }

        contenitore.innerHTML = "";

        recensioni.forEach(function (recensione, indice) {

            const elemento = document.createElement("div");

            elemento.className =
                "carousel-item text-center" +
                (indice === 0 ? " active" : "");

            const autore =
                recensione.username !== undefined
                    ? recensione.username
                    : "Utente";

            const testo =
                recensione.testo !== undefined
                    ? recensione.testo
                    : "";

            elemento.innerHTML = `
                <blockquote class="blockquote">
                    <p>${testo}</p>
                </blockquote>

                <p class="text-success fw-bold mb-0">
                    ${autore}
                </p>
            `;

            contenitore.appendChild(elemento);
        });

    } catch (errore) {

        contenitore.innerHTML = `
            <div class="carousel-item active text-center">
                <p class="text-muted mb-0">
                    Le recensioni saranno disponibili
                    dopo il collegamento del servizio AJAX.
                </p>
            </div>
        `;
    }
}

async function inserisciRecensione(evento) {

    evento.preventDefault();

    const campoTesto =
        document.getElementById("testo-recensione");

    const messaggio =
        document.getElementById("messaggio-recensione");

    const tokenElemento =
        document.querySelector('meta[name="_csrf"]');

    const headerElemento =
        document.querySelector('meta[name="_csrf_header"]');

    const headers = {
        "Content-Type": "application/json"
    };

    if (tokenElemento !== null &&
        headerElemento !== null &&
        tokenElemento.content !== "" &&
        headerElemento.content !== "") {

        headers[headerElemento.content] =
            tokenElemento.content;
    }

    try {
        const risposta = await fetch("/api/recensioni", {
            method: "POST",
            headers: headers,
            body: JSON.stringify({
                testo: campoTesto.value
            })
        });

        if (!risposta.ok) {
            throw new Error("Errore durante il salvataggio");
        }

        campoTesto.value = "";

        messaggio.innerHTML = `
            <div class="alert alert-success">
                Recensione inserita correttamente.
            </div>
        `;

        await caricaRecensioni();

    } catch (errore) {

        messaggio.innerHTML = `
            <div class="alert alert-warning">
                Il servizio recensioni non è ancora stato collegato.
            </div>
        `;
    }
}