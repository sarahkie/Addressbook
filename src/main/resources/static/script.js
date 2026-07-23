let currentEditId = null; //variable für die speicherung von einem kontakt der gerade bearbeitet wird

async function loadContacts(searchTerm) { //Funktion für vorhandene Kontakte anzeigen
    let url = '/app/v1/person';
    if (searchTerm) {
        url += `?name=${searchTerm}`;
    }
    const response = await fetch(url); //hier wird mein Endpunkt abgeholt!
    const contacts = await response.json(); //hier wird die Antwort mit dem JSON Body abgewartet und gespeichert

    const tableBody = document.getElementById('contactTableBody'); //hier wird auf das <table> Element im HTML zugegriffen.
    tableBody.innerHTML = '';

    for (const person of contacts) { //in dem ul Element wird für jeden Kontakt ein <li> Element erstellt und als Kind hinzugefügt

        const row = document.createElement('tr');

        const nameCell = document.createElement('td');
        nameCell.textContent = person.name;

        const phoneCell = document.createElement('td');
        phoneCell.textContent = person.phoneNumber;

        const emailCell = document.createElement('td');
        emailCell.textContent = person.emailAddress;

        const actionsCell = document.createElement('td');

        const deleteButton = document.createElement('button');

        const trashIcon = document.createElement('img');
        trashIcon.src = 'images/trash-64.png';
        trashIcon.alt = 'Löschen';
        trashIcon.width = 16;
        trashIcon.height = 16;
        deleteButton.appendChild(trashIcon);
        deleteButton.onclick = () => deleteContact(person.id);

        const editButton = document.createElement('button');
        const editIcon = document.createElement('img');
        editIcon.src = 'images/edit.png';
        editIcon.alt = 'bearbeiten';
        editIcon.width = 16;
        editIcon.height = 16;
        editButton.appendChild(editIcon);
        editButton.onclick = () => editContact(person.id, person.name, person.phoneNumber, person.emailAddress)

        actionsCell.appendChild(editButton);
        actionsCell.appendChild(deleteButton);


        row.appendChild(nameCell);
        row.appendChild(phoneCell);
        row.appendChild(emailCell);
        row.appendChild(actionsCell)
        tableBody.appendChild(row);
    }
}

function searchContacts() {
    const searchTerm = document.getElementById('searchInput').value;
    loadContacts(searchTerm);
}

async function addContact() { //Methode zum Hinzufügen eines Kontakts
    const name = document.getElementById('nameInput').value; //nimmt sich die Textinputs aus den Eingabefeldern, i guess?
    const phoneNumber = document.getElementById('phoneInput').value;
    const emailAddress = document.getElementById('emailInput').value;

    await fetch('/app/v1/person', { //hier wieder ein Endpunkt, wo jetzt ein Post abgesetzt wird
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({name: name, phoneNumber: phoneNumber, emailAddress: emailAddress}) //der Body wird in JSON umgewandelt
    });

    document.getElementById('nameInput').value = '';
    document.getElementById('phoneInput').value = '';
    document.getElementById('emailInput').value = '';

    loadContacts();
}

async function deleteContact(id) {
    await fetch(`/app/v1/person/${id}`, { //Achtung, da sind "Backticks", keine einfachen Anführungszeichen!!
        method: 'DELETE',
    });

    loadContacts();
}

function editContact(id, currentName, currentPhone, currentEmailAddress) {
    currentEditId = id;
    document.getElementById('editNameInput').value = currentName;
    document.getElementById('editPhoneInput').value = currentPhone;
    document.getElementById('editEmailInput').value = currentEmailAddress;
    document.getElementById('editModal').style.display = 'block'; //das Modal wird sichtbar!
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none'; //das Modal wird wieder unsichtbar
}

async function saveEdit() {
    const newName = document.getElementById('editNameInput').value;
    const newPhoneNumber = document.getElementById('editPhoneInput').value;
    const newEmailAddress = document.getElementById('editEmailInput').value;

    await fetch(`/app/v1/person/${currentEditId}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({name: newName, phoneNumber: newPhoneNumber, emailAddress: newEmailAddress})
    });

    closeEditModal(); //nach dem Speichern wird das Modal wieder unsichtbar gemacht
    loadContacts();
}

loadContacts();
