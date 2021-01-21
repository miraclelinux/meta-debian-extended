# base recipe: meta/recipes-devtools/python/python3-cython_0.29.21.bb
# base branch: master
# base commit: 7899bbe61963b4f998fcd63c83620751713b8efb

inherit debian-package
require recipes-debian/sources/cython.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/Cython-${PV}"
S = "${WORKDIR}/Cython-${PV}"

inherit setuptools3
require python-cython.inc

RDEPENDS_${PN} += "\
    python3-setuptools \
"

# running build_ext a second time during install fails, because Python
# would then attempt to import cythonized modules built for the target
# architecture.
DISTUTILS_INSTALL_ARGS += "--skip-build"

do_install_append() {
    # rename scripts that would conflict with the Python 2 build of Cython
    mv ${D}${bindir}/cython ${D}${bindir}/cython3
    mv ${D}${bindir}/cythonize ${D}${bindir}/cythonize3
    mv ${D}${bindir}/cygdb ${D}${bindir}/cygdb3
}

