import React from "react";
import { Switch, Route, Link } from "react-router-dom";

import DiseasesList from "../components/diseasePages/disease-list.component";
import DiseaseAdd from "../components/diseasePages/disease-add.component";
import Disease from "../components/diseasePages/disease.component";
import MainLayout from "../components/MainLayout";

const BoardDisease = () => {
  return (
    <MainLayout>
      <div className="container">
        <Link to={"/disease"} />
        <Switch>
          <Route exact path={"/disease"} component={DiseasesList} />
          <Route exact path="/disease/add" component={DiseaseAdd} />
          <Route exact path="/disease/:id" component={Disease} />
        </Switch>
      </div>
    </MainLayout>
  );
};

export default BoardDisease;
