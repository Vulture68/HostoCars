import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Redirect, Route, Router, Switch } from 'react-router-dom';
import { createBrowserHistory } from 'history';

import { ThemeProvider } from '@material-ui/styles';
import { Box, CssBaseline } from '@material-ui/core';

import 'simplebar';

import { AppBar, MenuBar } from 'components';
import { CarsPage, NotFoundPage } from 'pages';

import { store } from 'store';
import { theme } from 'resources';

import 'simplebar/dist/simplebar.min.css';
import './index.scss';

function unregister() {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.ready.then(registration => {
            registration.unregister();
        });
    }
}

ReactDOM.render(
    <ThemeProvider theme={theme}>
        <Provider store={store}>
            <Router history={createBrowserHistory()}>
                <CssBaseline />

                <main data-simplebar>
                    <AppBar />

                    <Box className='Page'>
                        <Switch>
                            <Redirect exact from='/' to='/cars' />
                            <Route exact path='/cars' component={CarsPage} />
                            <Route exact push component={NotFoundPage} />
                        </Switch>
                    </Box>

                    <MenuBar />
                </main>
            </Router>
        </Provider>
    </ThemeProvider>,
    document.getElementById('root')
);

unregister();
