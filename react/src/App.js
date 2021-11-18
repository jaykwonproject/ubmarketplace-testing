
import './App.css';
import Profile from './profile';
import Home from './home';
import Login from './login';
import {Route, Switch, HashRouter} from "react-router-dom"
import Register from './register';
import Sell from './sell';
import Listing from './listing';
import Item_Detail from './item_detail';
import Edit_Item from './edit_item';


function App() {
  return (
      <HashRouter>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/profile" component={Profile} />
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} />
          <Route path="/sell" component={Sell} />
          <Route exact path="/item/:id" component={Item_Detail} />
          <Route path="/listing" component={Listing} />
          <Route exact path="/item/edit/:id" component={Edit_Item} />
        </Switch>
      </HashRouter>
  );
}

export default App;
