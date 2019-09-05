# base recipe: meta-openembedded/meta-python/recipes-devtools/python/python-pycrypto_2.6.1.bb
# base branch: warrior
# base commit: c06be20b249d023849726ee752b76816ccf2e9f4

inherit distutils
require python-pycrypto.inc

# We explicitly call distutils_do_install, since we want it to run, but
# *don't* want the autotools install to run, since this package doesn't
# provide a "make install" target.
do_install() {
       distutils_do_install
}
