# base recipe: meta/recipes-devtools/python/python3-setuptools_40.8.0.bb
# base branch: warrior
# base commit: 806de34fda21fb1ab38d6b00566eb3d538d574d0

require python-setuptools.inc
inherit setuptools3

do_install_append() {
    mv ${D}${bindir}/easy_install ${D}${bindir}/easy3_install
}
