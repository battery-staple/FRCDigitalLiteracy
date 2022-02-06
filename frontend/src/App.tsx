import React, { Suspense, lazy } from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Helmet} from "react-helmet";

const TeacherHomepage = lazy(() => import("./TeacherHomepage"));

function App() {
  return (
      <div>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={
                      <div className="App">
                          <Suspense fallback={<div>Loading...</div>}>
                              <TeacherHomepage cards={[
                                  { name: "Body Image", description: "ppl thing body bad", url: "/test", progress: 1 },
                                  { name: "Cyberbullying", description: "ppl mean to each other" , url: "https://google.com", progress: 0.3 },
                                  { name: "Misinformation", description: "ppl think stuf thats wrong", url: "https://google.com", progress: 0 },
                                  { name: "Insensitive Language", description: "ppl use bad words", url: "https://google.com", progress: 1 },
                                  { name: "Physical Health", description: "ppl hurt htmeslelves", url: "https://google.com", progress: 1 },
                                  { name: "Narcissism", description: "ppl think theyre way better than they are", url: "https://google.com", progress: 0.4 },
                                  { name: "Public Appearance", description: "ppl give up too much info and others r like bruh and they judge them for stuff and the ppld ont even realize and its bad", url: "https://google.com", progress: 0.84 },
                              ]}/>
                          </Suspense>
                      </div>
                  } />

                  <Route path="/test" element={<p>hi</p>} />
              </Routes>
          </BrowserRouter>
      </div>
  );
}

export default App;
