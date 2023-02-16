# Incorporated the following commits
# https://github.com/yoctoproject/poky/commit/6e21f8ad839162a6286481b9b58e7d4b33519cd1

# The pkg-resources module can be used by itself, without the package downloader
# and easy_install. Ship it in a separate package so that it can be used by
# minimal distributions.
PACKAGES =+ "${PYTHON_PN}-pkg-resources "
FILES_${PYTHON_PN}-pkg-resources = "${PYTHON_SITEPACKAGES_DIR}/pkg_resources/*"
# Due to the way OE-Core implemented native recipes, the native class cannot
# have a dependency on something that is not a recipe name. Work around that by
# manually setting RPROVIDES.
RDEPENDS_${PN}_append = " ${PYTHON_PN}-pkg-resources"
RPROVIDES_append_class-native = " ${PYTHON_PN}-pkg-resources-native"
