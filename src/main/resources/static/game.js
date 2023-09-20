document.addEventListener("DOMContentLoaded", function () {
    const maze = document.getElementById("maze");
    var p1 = document.getElementById("p1");

    // Tamanho da janela do jogo
    var mazeWidth = 780; // Reduzido para 780
    var mazeHeight = 780; // Reduzido para 780

    // Tamanho do jogador
    var playerWidth = 20;
    var playerHeight = 20;

    // Posição inicial do jogador
    var playerTop = 400;
    var playerLeft = 400;

    // Função para mover o jogador
    function movePlayer() {
        p1.style.top = playerTop + "px";
        p1.style.left = playerLeft + "px";
    }

    // Adicione um ouvinte de eventos para a tecla pressionada
    window.addEventListener("keydown", function(event) {
        var mv = event.keyCode;

        // CIMA
        if (mv === 87) { // Tecla "W"
            if (playerTop > 20) {
                playerTop -= 10;
            }
        }

        // BAIXO
        if (mv === 83) { // Tecla "S"
            if (playerTop + playerHeight < mazeHeight) {
                playerTop += 10;
            }
        }

        // ESQUERDA
        if (mv === 65) { // Tecla "A"
            if (playerLeft > 20) {
                playerLeft -= 10;
            }
        }

        // DIREITA
        if (mv === 68) { // Tecla "D"
            if (playerLeft + playerWidth < mazeWidth) {
                playerLeft += 10;
            }
        }

        // Atualiza a posição do jogador
        movePlayer();

        // Envie o movimento para o servidor via WebSocket
        var move = {
            playerId: "player1", // Identificador único do jogador
            newX: playerLeft,
            newY: playerTop
        };

        // Envie o objeto "move" como uma mensagem para o servidor WebSocket
        socket.send(JSON.stringify(move));
    });

    // Inicialize o jogo definindo a posição inicial do jogador
    movePlayer();

    // Crie uma nova conexão WebSocket
    const socket = new WebSocket('ws://localhost:8080/game'); // Substitua a URL pelo seu endpoint WebSocket

    // Defina um listener para quando a conexão for estabelecida
    socket.addEventListener('open', (event) => {
        console.log('Conexão WebSocket estabelecida.');
    });

    // Defina um listener para quando uma mensagem for recebida
    socket.addEventListener('message', (event) => {
        const mensagem = event.data;
        console.log('Mensagem recebida: ' + mensagem);
        // Você pode adicionar código para processar a mensagem aqui
    });

    // Defina um listener para lidar com erros
    socket.addEventListener('error', (event) => {
        console.error('Erro na conexão WebSocket.');
    });

    // Defina um listener para quando a conexão for fechada
    socket.addEventListener('close', (event) => {
        if (event.wasClean) {
            console.log('Conexão WebSocket fechada corretamente.');
        } else {
            console.error('Conexão WebSocket interrompida por um erro.');
        }
    });
});
