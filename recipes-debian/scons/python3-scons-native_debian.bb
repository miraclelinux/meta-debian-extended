# base recipe: meta/recipes-devtools/python/python3-scons-native_3.1.2.bb
# base branch: master
# base commit: 44b46ae6c65448901477def57a45d5a4ecc2d246
# http://cgit.openembedded.org/openembedded-core/tree/meta/recipes-devtools/python/python3-scons-native_3.1.2.bb?h=master

require python3-scons_${PV}.bb
inherit native python3native
DEPENDS = "python3-native python3-setuptools-native"

do_install_append() {
    create_wrapper ${D}${bindir}/scons SCONS_LIB_DIR='${STAGING_DIR_HOST}/${PYTHON_SITEPACKAGES_DIR}' PYTHONNOUSERSITE='1'
}
