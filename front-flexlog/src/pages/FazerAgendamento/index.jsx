import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import Modal from "../../components/Modal";
import * as C from "./styles";
import axios from "axios";

const FazerAgendamento = ({ pacoteId, token }) => {
  const [data, setData] = useState(null);
  const [horario, setHorario] = useState("");
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMsg, setModalMsg] = useState("");

  const horariosDisponiveis = [
    "08:00-09:00",
    "09:00-10:00",
    "10:00-11:00",
    "11:00-12:00",
    "12:00-13:00",
    "13:00-14:00",
    "14:00-15:00",
    "15:00-16:00",
    "16:00-17:00",
  ];

  const handleAgendar = async () => {
    if (!data || !horario) {
      setModalMsg("Escolha um dia e um horário para agendar.");
      setModalOpen(true);
      return;
    }

    try {
      const response = await axios.post(
        `http://localhost:8080/tentativa-entrega/${pacoteId}`,
        {
          dataAgendamento: data.toISOString().split("T")[0],
          horario: horario,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setModalMsg("Agendamento realizado com sucesso!");
      setModalOpen(true);
    } catch (err) {
      setModalMsg(
        err.response?.data || "Erro ao efetuar agendamento. Tente novamente."
      );
      setModalOpen(true);
    }
  };

  return (
    <C.Container>
      <C.Content>
        <C.Title>Faça seu Agendamento</C.Title>

        <C.CalendarWrapper>
          <Calendar
            onChange={setData}
            value={data}
            minDate={new Date()}
          />
        </C.CalendarWrapper>

        <C.SelectWrapper>
          <C.Label>Horário</C.Label>
          <C.Select
            value={horario}
            onChange={(e) => setHorario(e.target.value)}
          >
            <option value="">Selecione um horário</option>
            {horariosDisponiveis.map((h) => (
              <option key={h} value={h}>
                {h}
              </option>
            ))}
          </C.Select>
        </C.SelectWrapper>

        <C.Button onClick={handleAgendar}>Fazer Agendamento</C.Button>
      </C.Content>

      <Modal open={modalOpen} onClose={() => setModalOpen(false)}>
        <p>{modalMsg}</p>
      </Modal>
    </C.Container>
  );
};

export default FazerAgendamento;
