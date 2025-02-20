import React from "react";
import { RecoilRoot } from "recoil";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Header from "./components/header/Header";
import Footer from "./components/footer/Footer";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import CreateFeed from "./pages/FeedCRUD/CreateFeed";
import PetFeed from "./pages/PetFeed";
import UpdateFeed from "./pages/FeedCRUD/UpdateFeed";
import Chatting from "./pages/Chatting";
import PetMap from "./pages/PetMap";
import MapDetail from "./pages/MapDetail";
import MyMap from "./pages/MyMap";
import MyPage from "./pages/MyPage";
import PetProfile from "./pages/PetProfile";
const App: React.FC = () => {
  return (
    <RecoilRoot>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/signUp" element={<SignUp />} />
          <Route path="/create" element={<CreateFeed />} />
          <Route path="/update" element={<UpdateFeed />} />
          <Route path="/feeds" element={<PetFeed />} />
          <Route path="/chat" element={<Chatting />} />
          <Route path="/petmap" element={<PetMap />} />
          <Route path="/petmap/1" element={<MapDetail />} />
          <Route path="/member/:memberId/mymap" element={<MyMap />} />
          <Route path="/myPage" element={<MyPage />} />
          <Route path="/petProfile" element={<PetProfile />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </RecoilRoot>
  );
};

export default App;
