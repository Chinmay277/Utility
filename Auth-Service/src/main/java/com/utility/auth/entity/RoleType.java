package com.utility.auth.entity;

/**
 * Enum representing system roles for TCS UVision-style
 * inspection and utility asset management platform.
 */
public enum RoleType {

    // Core system roles
    ROLE_ADMIN,          // Full system access, manage users/roles
    ROLE_SUPER_ADMIN,    // Enterprise-level control, compliance oversight

    // Operational roles
    ROLE_INSPECTOR,      // Performs field inspections (drones, IoT sensors, manual checks)
    ROLE_ANALYST,        // Reviews inspection data, vegetation encroachment, wildfire risk
    ROLE_MANAGER,        // Approves reports, schedules maintenance, assigns inspectors
    ROLE_AUDITOR,        // Ensures compliance, reviews logs, regulatory reporting
    ROLE_MAINTENANCE,    // Executes corrective actions (repairs, vegetation clearance)

    // Specialized roles
    ROLE_AI_OPERATOR,    // Oversees AI/ML models for anomaly detection
    ROLE_SECURITY_OFFICER, // Ensures cybersecurity of inspection data
    ROLE_VIEWER          // Read-only access for stakeholders (utility executives, regulators)
}