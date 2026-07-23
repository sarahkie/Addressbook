

async function loadContacts() { //Funktion für vorhandene Kontakte anzeigen
    const response = await fetch('/app/v1/person'); //hier wird mein Endpunkt abgeholt!
    const contacts = await response.json(); //hier wird die Antwort mit dem JSON Body abgewartet und gespeichert

    const tableBody = document.getElementById('contactTableBody'); //hier wird auf das <table> Element im HTML zugegriffen.
    tableBody.innerHTML = '';

    for (const person of contacts) { //in dem ul Element wird für jeden Kontakt ein <li> Element erstellt und als Kind hinzugefügt

        const row = document.createElement('tr');

        const nameCell = document.createElement('td');
        nameCell.textContent = person.name;

        const phoneCell=document.createElement('td');
        phoneCell.textContent=person.phoneNumber;

        const emailCell =document.createElement('td');
        emailCell.textContent=person.emailAddress;

        const actionsCell = document.createElement('td');

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Löschen';
        deleteButton.onclick = () => deleteContact(person.id);
        const editButton = document.createElement('button');
        editButton.textContent = "Bearbeiten";
        editButton.onclick = () => editContact(person.id, person.name, person.phoneNumber,person.emailAddress)

        actionsCell.appendChild(deleteButton);
        actionsCell.appendChild(editButton);

        row.appendChild(nameCell);
        row.appendChild(phoneCell);
        row.appendChild(emailCell);
        row.appendChild(actionsCell)
        tableBody.appendChild(row);
    }
}

async function addContact() { //Methode zum Hinzufügen eines Kontakts
    const name = document.getElementById('nameInput').value; //nimmt sich die Textinputs aus den Eingabefeldern, i guess?
    const phoneNumber = document.getElementById('phoneInput').value;
    const emailAddress=document.getElementById('emailInput').value;

    await fetch('/app/v1/person', { //hier wieder ein Endpunkt, wo jetzt ein Post abgesetzt wird
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({name: name, phoneNumber: phoneNumber, emailAddress: emailAddress}) //der Body wird in JSON umgewandelt
    });

    document.getElementById('nameInput').value = '';
    document.getElementById('phoneInput').value = '';
    document.getElementById('emailInput').value='';

    loadContacts();
}

async function deleteContact(id) {
    await fetch(`/app/v1/person/${id}`, { //Achtung, da sind "Backticks", keine einfachen Anführungszeichen!!
        method: 'DELETE',
    });

    loadContacts();
}

async function editContact(id, currentName, currentPhone, currentEmailAddress) {
    const newName = prompt('Neuer Name:', currentName);
    const newPhoneNumber = prompt('Neue Telefonnummer:', currentPhone);
    const newEmailAddress = prompt('Neue E-Mail-Adresse:', currentEmailAddress);
    await fetch(`/app/v1/person/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({name: newName, phoneNumber: newPhoneNumber, emailAddress: newEmailAddress})
    });
    loadContacts();
}

loadContacts();
