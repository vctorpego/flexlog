import React, { useEffect } from "react";
import Modal from "../Modal";
import { CheckCircle, XCircle, Info } from "lucide-react";

const iconMap = {
  success: <CheckCircle className="w-12 h-12 text-green-500" />,
  error: <XCircle className="w-12 h-12 text-red-500" />,
  info: <Info className="w-12 h-12 text-blue-500" />,
};

const FeedbackModal = ({ open, onClose, message, type = "info" }) => {
  // Fecha com ESC
  useEffect(() => {
    const handleEsc = (e) => {
      if (e.key === "Escape") onClose();
    };
    if (open) document.addEventListener("keydown", handleEsc);
    return () => document.removeEventListener("keydown", handleEsc);
  }, [open, onClose]);

  return (
    <Modal open={open} onClose={onClose}>
      <div className="flex flex-col items-center justify-center gap-4 p-4 text-center">
        {iconMap[type]}
        <p
          className={`text-lg font-semibold ${
            type === "success"
              ? "text-green-700"
              : type === "error"
              ? "text-red-700"
              : "text-blue-700"
          }`}
        >
          {message}
        </p>
        <button
          onClick={onClose}
          className={`mt-2 px-6 py-2 rounded font-semibold
            ${
              type === "success"
                ? "bg-green-600 hover:bg-green-700 text-white"
                : type === "error"
                ? "bg-red-600 hover:bg-red-700 text-white"
                : "bg-blue-600 hover:bg-blue-700 text-white"
            }
            transition-colors`}
        >
          Fechar
        </button>
      </div>
    </Modal>
  );
};

export default FeedbackModal;
