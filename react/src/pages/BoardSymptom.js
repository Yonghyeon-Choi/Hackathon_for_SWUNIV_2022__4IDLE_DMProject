import React, { useState, useEffect, Component } from "react";
import { Switch, Route, Link } from "react-router-dom";

import SymptomList from "../components/symptomPages/symptom-list.component";
import SymptomAdd from "../components/symptomPages/symptom-add.component";
import Symptom from "../components/symptomPages/symptom.component";
import MainLayout from "../components/MainLayout";

const BoardSymptom = () => {
  return (
    <MainLayout>
      <div className="container">
        <Link to={"/symptom"} />
        <Switch>
          <Route exact path={"/symptom"} component={SymptomList} />
          <Route exact path="/symptom/add" component={SymptomAdd} />
          <Route exact path="/symptom/:id" component={Symptom} />
        </Switch>
      </div>
    </MainLayout>
  );
};

export default BoardSymptom;
