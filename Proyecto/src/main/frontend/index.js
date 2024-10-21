let bearerToken = ""; // Aquí almacenaremos dinámicamente el token
let persons = []; // Almacenar la lista de personas
const apiBaseUrl = 'http://localhost:8080'; // Base URL para la API
let map; // Variable para almacenar el mapa

// Cargar Google Maps API y autenticar al cargar el DOM
document.addEventListener('DOMContentLoaded', async () => {
    try {
        await authenticate(); // Autenticarse antes de obtener la API Key
        const googleMapsApiKey = await fetchGoogleMapsApiKey();
        loadGoogleMaps(googleMapsApiKey);
    } catch (error) {
        console.error('Error al cargar la API Key o autenticación:', error);
    }
});

// Función para autenticar y obtener un nuevo token
async function authenticate() {
    try {
        const response = await fetch(`${apiBaseUrl}/usuarios/authenticate`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'X-ApiKey': '', //Se genera a la hora de crear un usuario en la base de datos
            },
            body: JSON.stringify({
                username: '', // Reemplaza con el usuario correcto
                password: '' // Reemplaza con la contraseña correcta
            }),
            mode: 'cors'
        });

        if (response.ok) {
            const token = await response.text();
            bearerToken = "Bearer " + token; // Guardar el token para usarlo más tarde
            console.log("Autenticación exitosa, token obtenido:", bearerToken);
        } else {
            console.error("Error durante la autenticación: ", response.status);
            throw new Error("Authentication failed");
        }
    } catch (error) {
        console.error("Error en la autenticación: ", error);
        throw error;
    }
}

// Función para obtener la clave de API de Google Maps
async function fetchGoogleMapsApiKey() {
    const response = await fetch(`${apiBaseUrl}/api/key`); // Asegúrate de que esta ruta esté configurada en tu backend
    if (!response.ok) {
        throw new Error('Error al obtener la API Key');
    }
    return await response.text();
}

// Cargar el script de Google Maps con la librería correcta
function loadGoogleMaps(apiKey) {
    const script = document.createElement('script');
    script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&v=weekly&libraries=marker,places`;
    script.defer = true;
    script.onerror = () => console.error('Error al cargar Google Maps');
    document.body.appendChild(script);
}

function initMap(personId = null) {
    const bangalore = { lat: 4.438213601624155, lng: -75.21420743545055 };
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 14,
        center: bangalore,
    });

    // Hacer la solicitud para obtener las coordenadas
    fetch("http://localhost:8080/Coordenadas/coordenadas", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'DELETE, POST, GET, OPTIONS',
            'Access-Control-Allow-Headers': 'Content-Type, Authorization, X-Requested-With',
            'Authorization': bearerToken
        },
        mode: 'cors'
    })
    .then(response => response.json())
    .then(json => {
        console.log(json);
        
        // Filtrar nombres únicos
        const uniquePersons = Array.from(new Set(json.map(coord => coord.marca)));

        persons = json; // Almacenar los datos de personas para uso posterior
        
        // Filtrar las coordenadas si se proporciona un ID de persona
        const coordinatesToShow = personId ? json.filter(coord => coord.persona === personId) : json;

        // Añadir marcadores para cada ubicación obtenida
        for (let coord of coordinatesToShow) {
            addMarker({ lat: coord.longitud, lng: coord.latitud }, map, coord.marca);
        }
        
        // Mostrar lista de personas
        displayPersonList(uniquePersons);
    })
    .catch(error => console.error("Error fetching coordinates:", error));
}


// Añade un marcador al mapa
function addMarker(location, map, label) {
    new google.maps.Marker({
        position: location,
        label: label, // Usar la etiqueta (marca) de la base de datos
        title: label, // Título del marcador, también puede ser la marca
        map: map,
    });
}



// Mostrar la lista de personas
function displayPersonList(uniquePersons) {
    const personListDiv = document.getElementById('personList');
    personListDiv.innerHTML = ''; // Limpiar la lista

    uniquePersons.forEach(person => {
        const button = document.createElement('button');
        button.innerText = person; // Usar el nombre único de la persona
        button.onclick = () => {
            // Filtrar el ID de persona según el nombre (suponiendo que el nombre es suficiente para identificar a la persona)
            const personData = persons.find(coord => coord.marca === person);
            initMap(personData ? personData.persona : null); // Actualizar el mapa con las coordenadas de la persona seleccionada
        };
        personListDiv.appendChild(button);
    });
}

// Mostrar todas las coordenadas
document.getElementById('showAll').onclick = () => {
    initMap(); // Cargar todas las coordenadas
};

// Mostrar la lista de personas al hacer clic en el botón
document.getElementById('showList').onclick = () => {
    displayPersonList(Array.from(new Set(persons.map(coord => coord.marca))));
};

// Asegúrate de que `initMap` sea accesible globalmente
window.initMap = initMap;
