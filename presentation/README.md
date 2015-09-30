# AngularJS Training

## Getting The Training Material

```
git clone https://github.com/codecentric/angularjs-training.git
cd angularjs-training
```

## Working With The Training Material
In order to work with the training material, you need to serve all files via an HTTP server. This can be any HTTP server which is capable of serving static assets. If you happen to have Python installed (which is installed by default on OS X and Linux), you can execute the following command to start an HTTP server in the current directory.

```
# with Python 2.x
python -m SimpleHTTPServer

# with Python 3.x
python -m http.server
```

You could also install [proxrox](https://github.com/bripkens/proxrox) and use it to serve all static assets via HTTPS and the SPDY protocol :-).

```
# please see the following section for Node.js installation instructions
npm install -g proxrox

# the install command only works on OS X, sorry
proxrox install

proxrox start --spdy

# open is only available on OS X, sorry
open https://localhost:4000
```

## Install Node.js
You need to have Node.js installed in order to execute the tests and lint
JavaScript files. OS X and Linux users should install Node.js via the
[Node Version Manager](https://github.com/creationix/nvm) (NVM). NVM makes it
easy to switch between installed Node.js versions and to install global
modules without super-user privileges. Windows users should follow the
instructions on the [official website](http://nodejs.org/).

### Node Version Manager installation
Make sure that you have Git and cURL installed.

```
# download and install NVM
curl https://raw.githubusercontent.com/creationix/nvm/v0.22.2/install.sh | bash

# reload bash
bash

# install Node.js version 0.10
nvm install 0.10
# use Node.js version 0.10
nvm use 0.10
# and use it by default
nvm alias default 0.10
```

## Source code linting
Source code can be linted via the command line or your favorite editor.

### Command Line
```
npm install -g eslint
eslint tasks/
```

### Atom Editor
 - Install the *linter* and *linter-eslint* packages.
 - Restart the editor

## Test execution
As of know, this only works in the solution for the HTTP task.

```
# install local dependencies
npm install
npm test
```
