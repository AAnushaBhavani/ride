import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import OfferRidePage from "./pages/OfferRidePage";
import BookRidePage from "./pages/BookRidePage";
import ProfilePage from "./pages/ProfilePage";
import RideHistoryPage from "./pages/RideHistoryPage";
import ProtectedRoute from "./components/ProtectedRoute";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route
          path="/offer-ride"
          element={
            <ProtectedRoute>
              <OfferRidePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/book-ride"
          element={
            <ProtectedRoute>
              <BookRidePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <ProfilePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/history"
          element={
            <ProtectedRoute>
              <RideHistoryPage />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}
