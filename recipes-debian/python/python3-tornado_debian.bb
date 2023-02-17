#base recipe: meta-openembedded/meta-python/recipes-devtools/python/python3-tornado_5.1.bb
#base branch: warrior
#base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

inherit pypi setuptools3
require python-tornado.inc

# Requires _compression which is currently located in misc
RDEPENDS_${PN} += "\
    ${PYTHON_PN}-misc \
    "
