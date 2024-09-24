async function fetchHotels() {
  try {
    const response = await fetch("http://localhost:8080/hotels");
    if (!response.ok) {
      throw new Error("Erro ao buscar hotéis");
    }

    const hotels = await response.json();
    const select = document.getElementById("hotel");
    select.innerHTML =
      '<option value="" disabled selected>Selecione o hotel</option>';
    hotels.forEach((hotel) => {
      const option = document.createElement("option");
      option.value = hotel.id;
      option.textContent = `${hotel.city} - ${hotel.name}`;

      option.dataset.city = hotel.city;
      option.dataset.name = hotel.name;
      option.dataset.stars = hotel.stars;

      select.appendChild(option);
    });
  } catch (error) {
    console.error("Erro ao preencher o select:", error);
  }
}
document.addEventListener("DOMContentLoaded", fetchHotels);

async function fetchAvailableRooms(hotelId) {
  try {
    const hotelSelect = document.getElementById("hotel");
    hotelSelect.addEventListener("change", () => {
      const selectedHotel = selectedHotel.value;
    });
    const checkIn = document.getElementById("checkin").value;
    const checkOut = document.getElementById("checkout").value;
    const guests = document.getElementById("guests").value;
    checkInDate = new Date(checkIn).toISOString().split("T")[0];
    checkOutDate = new Date(checkOut).toISOString().split("T")[0];
    const response = await fetch(
      `http://localhost:8080/rooms/available?hotelId=${hotelId}&checkIn=${checkInDate}&checkOut=${checkOutDate}&guestsNumber=${guests}`
    );
    if (!response.ok) {
      throw new Error("Erro ao buscar hotéis");
    }
    const roomsAvailable = await response.json();
    return roomsAvailable;
  } catch (error) {
    console.log(error);
  }
}

document
  .getElementById("searchForm")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const hotelSelect = document.getElementById("hotel");
    const hotelId = hotelSelect.value;

    if (!hotelId) {
      throw new Error("Selecione um hotel");
    }

    try {
      const roomsAvailable = await fetchAvailableRooms(hotelId);
      displayAvailableRooms(roomsAvailable);
    } catch (error) {
      console.log("Erro ao buscar quartos: ", error);
    }
  });

async function displayAvailableRooms(rooms) {
  const mainContentTitle = document.getElementById("title");
  mainContentTitle.textContent = "Quartos disponíveis";
  const subTitle = document.querySelector(".main-content p");
  subTitle.innerText = "";

  const roomList = document.getElementById("roomList");
  roomList.innerHTML = "";

  if (rooms.length === 0) {
    subTitle.innerText = "Nenhum quarto disponível no período solicitado!";
  } else {
    rooms.forEach((room) => {
      const roomCard = document.createElement("div");
      roomCard.classList.add("col");
      roomCard.innerHTML = `
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Quarto ${
                              room.roomNumber
                            }</h5>
                            <p class="card-text">
                                <strong>Capacidade:</strong> ${
                                  room.capacity
                                } pessoas<br>
                                <strong>Camas de solteiro:</strong> ${
                                  room.singleBeds
                                }<br>
                                <strong>Camas de casal:</strong> ${
                                  room.coupleBeds
                                }<br>
                                <strong>Preço por diária:</strong> R$${room.diaryValue.toFixed(
                                  2
                                )}<br>
                                <hr>
                                <strong>Hotel:</strong> ${room.hotel.name} (${
        room.hotel.stars
      } ⭐)<br>
                                <strong>Cidade:</strong> ${room.hotel.city}
                            </p>
                            <button class="btn btn-primary reservar-btn" data-room-id="${
                              room.id
                            }">Reservar</button>
                        </div>
                    </div>
                `;
      roomList.appendChild(roomCard);
    });

    document.querySelectorAll(".reservar-btn").forEach((button) => {
      button.addEventListener("click", function () {
        const roomId = this.dataset.roomId;
        const selectedRoom = rooms.find((room) => room.id === parseInt(roomId));
        reservarQuarto(selectedRoom);
      });
    });
  }
}

function reservarQuarto(room) {
  const isLoggedIn = localStorage.getItem("isLoggedIn");
  if (!isLoggedIn) {
    alert("Efetue o login primeiro!");
    document.getElementsByName("username").classList.add("highlight");
    document.getElementsByName("password").classList.add("highlight");
  } else {
    localStorage.setItem("selectedRoom", JSON.stringify(room));
    const checkIn = document.getElementById("checkin").value;
    const checkOut = document.getElementById("checkout").value;
    const guestsNumber = Number(document.getElementById("guests").value);
    localStorage.setItem("checkIn", checkIn);
    localStorage.setItem("checkOut", checkOut);
    localStorage.setItem("guestsNumber", guestsNumber);
    
    // Fazer a requisição para /prebooking com credenciais
    fetchPrebookingData();
  }
}

// Função para efetuar a requisição à API de prebooking
async function fetchPrebookingData() {
  try {
    const response = await fetch("http://localhost:8080/prebooking", {
      method: "GET", // ou "POST", dependendo de como o seu endpoint foi configurado
      credentials: "include", // Adiciona os cookies de autenticação
    });

    if (response.ok) {
      window.location.href = "http://localhost:8080/prebooking"; // Redirecionar para a página de prebooking
    } else {
      console.error("Erro ao acessar prebooking", response.status);
      alert("Erro ao acessar a página de confirmação da reserva.");
    }
  } catch (error) {
    console.error("Erro ao acessar prebooking:", error);
  }
}


document.querySelectorAll(".reservar-btn").forEach((button) => {
  button.addEventListener("click", () => {
    const roomId = button.dataset.roomId;
    const selectedRoom = rooms.find((room) => room.id === parseInt(roomId));
    reservarQuarto(selectedRoom);
  });
});

// document.addEventListener("DOMContentLoaded", function () {
//   const authContainer = document.getElementById("authContainer");
//   const loginForm = document.getElementById("loginForm");
//   const userDetails = document.getElementById("userDetails");
//   const usernameSpan = document.getElementById("username");
//   const logoutButton = document.getElementById("logoutButton");

//   const loggedInUser = localStorage.getItem("loggedInUser");

//   if (loggedInUser) {
//     loginForm.classList.add("d-none");
//     userDetails.classList.remove("d-none");
//     usernameSpan.textContent = loggedInUser;
//   }
//   logoutButton.addEventListener("click", function () {
//     localStorage.removeItem("loggedInUser");
//     window.location.reload();
//   });
// });

async function fetchCurrentUser() {
  try {
    const response = await fetch("http://localhost:8080/auth/currentUser");
    if (response.ok) {
      const userData = await response.json();
      console.log("UserData: ", userData.id);
      localStorage.setItem("user", JSON.stringify(userData));
      localStorage.setItem("clientId", userData.id)
      localStorage.setItem("isLoggedIn", true);
      displayUserInfo(userData);
    } else {
      console.log("Não foi possível obter os dados do usuário.");
      localStorage.removeItem("isLoggedIn");
    }
  } catch (error) {
    console.error("Erro ao obter dados do usuário:", error);
    localStorage.removeItem("isLoggedIn");
  }
}

function displayUserInfo(user) {
  const loginContainer = document.querySelector(".login-container");
  loginContainer.innerHTML = `
      <span>
  Bem-vindo, ${user.name} | 
  <a href="/client_bookings" style="text-decoration: none; color: inherit;">
    <b> Acesse sua conta</b>
  </a>
</span>
<button id="logoutButton" class="btn btn-light ms-2">Sair</button>

    `;

  document.getElementById("logoutButton").addEventListener("click", () => {
    localStorage.removeItem("user");
    window.location.href = "/logout";
  });
}

document.addEventListener("DOMContentLoaded", () => {
  const user = JSON.parse(localStorage.getItem("user"));
  if (user) {
    displayUserInfo(user);
  } else {
    fetchCurrentUser();
  }
});
document.getElementById("logoutButton").addEventListener("click", async () => {
  try {
    const response = await fetch("/logout", {
      method: "POST", 
      credentials: "include",
    });

    if (response.ok) {
      localStorage.removeItem("user");
      window.location.href = "/";
    } else {
      console.error("Erro ao tentar sair:", response.status);
      alert("Erro ao tentar sair.");
    }
  } catch (error) {
    console.error("Erro ao tentar logout:", error);
  }
});
