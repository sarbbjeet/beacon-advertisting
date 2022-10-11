import {combineReducers} from '@reduxjs/toolkit';
import bleConfigReducer from './bleConfig';

export default combineReducers({
  bleConfig: bleConfigReducer,
});
