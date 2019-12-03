# base recipe: meta-openembedded/meta-python/recipes-devtools/python/python3-psutil_5.6.1.bb
# base branch: warrior
# base commit: 77e5da1f24620aaf050d20b1fbfe00250c0fdda1

inherit pypi setuptools3
require python-psutil.inc

RDEPENDS_${PN} += "${PYTHON_PN}-netclient"
