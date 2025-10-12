# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-scott-karl;protocol=https;branch=main \
           file://misc-modules-init \
           file://module_load \
           file://module_unload \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "7fd35aaadcf22afd12e1b16fd634c672ea31df33"

S = "${WORKDIR}/git"

# Inherit module first, then update-rc.d
inherit module update-rc.d

# Flag this package as one which uses init scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "misc-modules-init"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/misc-modules LDDINC=${S}/include"

# Include the init script and helper scripts in the package
FILES:${PN} += "${sysconfdir}/init.d/misc-modules-init"
FILES:${PN} += "${bindir}/module_load ${bindir}/module_unload"

do_install:append () {
	# Install the init script for misc-modules
	# ${sysconfdir} typically expands to /etc
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/misc-modules-init ${D}${sysconfdir}/init.d/
	
	# Install the module_load and module_unload helper scripts
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/module_load ${D}${bindir}/
	install -m 0755 ${WORKDIR}/module_unload ${D}${bindir}/
}
